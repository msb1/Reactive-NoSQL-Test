package model

import java.util.Date

import com.outworkers.phantom.dsl._
import connector.Connector

import scala.collection.immutable.{HashMap, Map}
import scala.concurrent.Future

case class EpdData(CurrentTime: String, Topic: String, Categories: HashMap[String, Int], Sensors: HashMap[String, Double], Result: Int)

case class DataRecord(id: Long, currentTime: Date, topic: String, categories: Map[String, Int], sensors: Map[String, Double], result: Int)

class DataRecordsModel extends Table[DataRecordsModel, DataRecord] {

  override def tableName: String = "epd01"

  object id extends LongColumn with PartitionKey

  object currentTime extends DateColumn

  object topic extends StringColumn

  object categories extends MapColumn[String, Int]

  object sensors extends MapColumn[String, Double]

  object result extends IntColumn

  override def fromRow(row: Row): DataRecord = {
    DataRecord(id(row), currentTime(row), topic(row), categories(row), sensors(row), result(row))
  }

  def getById(id: Long): Future[Option[DataRecord]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def getAll(): Future[List[DataRecord]] = {
    select
      .all()
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getByDate(date: Date): Future[List[DataRecord]] = {
    select
      .where(_.currentTime lt date)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def deleteById(id: Long): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def deleteByDate(date: Date): Future[ResultSet] = {
    delete
      .where(_.currentTime lt date)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def deleteAll(): Future[ResultSet] = {
    truncate()
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def store(rec: DataRecord): Future[ResultSet] = {
    insert
      .value(_.id, rec.id)
      .value(_.currentTime, rec.currentTime)
      .value(_.topic, rec.topic)
      .value(_.categories, rec.categories)
      .value(_.sensors, rec.sensors)
      .value(_.result, rec.result)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  override implicit def space: KeySpace = Connector.space
  override implicit def session: Session = Connector.session
}

class BasicDatabase(override val connector: CassandraConnection) extends Database[BasicDatabase](connector) {

  object entries extends DataRecordsModel

}

object Database extends BasicDatabase(Connector.connector)