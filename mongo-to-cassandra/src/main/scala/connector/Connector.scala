package connector

import com.datastax.driver.core.{Cluster, SocketOptions}
import com.outworkers.phantom.connectors.SessionProvider
import com.outworkers.phantom.dsl._

import com.typesafe.config.ConfigFactory

object CassandraConnectionUri {
  private val cassandraConfig = ConfigFactory.load.getConfig("cassandra")
  val port = cassandraConfig.getInt("port")
  val host = cassandraConfig.getString("hosts")
  val keySpace = cassandraConfig.getString("keyspace")
}

object Connector extends SessionProvider {

  lazy val connector: CassandraConnection = ContactPoint.local
    .withClusterBuilder(
      _.withSocketOptions(
        new SocketOptions()
          .setConnectTimeoutMillis(20000)
          .setReadTimeoutMillis(20000)
      )
        .addContactPoint(CassandraConnectionUri.host)
        .withPort(CassandraConnectionUri.port)
    ).noHeartbeat().keySpace(
    KeySpace(CassandraConnectionUri.keySpace).ifNotExists().`with`(
      replication eqs SimpleStrategy.replication_factor(1)
    )
  )

  override def space: KeySpace = KeySpace(CassandraConnectionUri.keySpace)
  override val session: Session = connector.session
  val cluster: Cluster = connector.session.getCluster
}
