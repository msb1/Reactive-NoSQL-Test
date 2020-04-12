import java.text.SimpleDateFormat

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.mongodb.scaladsl.MongoSource
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import ch.qos.logback.classic.{Level, Logger}
import com.mongodb.reactivestreams.client.MongoClients
import com.outworkers.phantom.dsl.KeySpace
import model.{DataRecord, Database, EpdData}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

object Main extends App {

  // set Logger levels to WARN (to avoid excess verbosity)
  LoggerFactory.getLogger("org").asInstanceOf[Logger].setLevel(Level.WARN)
  LoggerFactory.getLogger("akka").asInstanceOf[Logger].setLevel(Level.WARN)

  // define ActorSystem and Materializer for akka streams
  implicit val actorSystem = ActorSystem("MongoToCassandra")
  implicit val materializer = ActorMaterializer()
  implicit val ex: ExecutionContext = ExecutionContext.Implicits.global
  implicit val space: KeySpace = KeySpace("barnwaldo")

  // Connect to MongoDB with reactive driver
  val codecRegistry = fromRegistries(fromProviders(classOf[EpdData]), DEFAULT_CODEC_REGISTRY)
  val client = MongoClients.create("mongodb://barnwaldo:shakeydog@192.168.131.3:27017/?authSource=admin")
  val db = client.getDatabase("barnwaldo")
  val epd01 = db.getCollection("epd01", classOf[EpdData]).withCodecRegistry(codecRegistry)

  // Use Alpakka MongoSource to get records from MongoDB collection
  val source: Source[EpdData, NotUsed] = MongoSource(epd01.find(classOf[EpdData]))
  val result: Future[Seq[EpdData]] = source.runWith(Sink.seq)
  implicit val timeout = Timeout(5 seconds)
  val epdData = Await.result(result, timeout.duration)
  println("Number of records read from edp01 MongoDB: " + epdData.length.toString)

  // convert timestamp string to date so that it can be used in time series searches
  val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  var dataRecords: Seq[DataRecord] = Seq.empty[DataRecord]
  var ctr = 0L
  epdData.foreach((epd) => {
    ctr += 1L
    val currentTime = formatter.parse(epd.CurrentTime)
    val dataRecord = DataRecord(ctr, currentTime, epd.Topic, epd.Categories, epd.Sensors, epd.Result)
    dataRecords = dataRecords :+ dataRecord
  })

  // Cassandra delete all records from table (start with an empty table for the data transfer)
  Database.entries.deleteAll()

  // Insert all DataRecords from MongoDB table into Cassandra table
  // Use await -- HERE ONLY -- to prevent BusyPoolException when trying to insert large Seq at once into Cassandra
  dataRecords.foreach((rec) => {
    Await.result(Database.entries.store(rec), 1 second)
  })

  // Read all DataRecords from Cassandra table to verify insertion
  val readData:Future[List[DataRecord]] = Database.entries.getAll()

  readData.onComplete( {
    case Success(data) => {
      // data.foreach{ println }
      println("Number of records read from edp01 Cassandra: " + data.length.toString)
    }
    case Failure(t) => println("Error in Cassandra getAll(): " + t.getMessage)
  })

}

