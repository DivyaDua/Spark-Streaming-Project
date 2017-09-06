package knoldus

import java.sql._
import org.apache.spark.internal.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

class CustomReceiver extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) with Logging {

  override def onStart(): Unit = {
    new Thread("Receiver") {
      logInfo("Inside start method")
      try {
        val driver = "org.postgresql.Driver"
        val url = "jdbc:postgresql://localhost:5432/test_db"
        val username: String = "postgres"
        val password: String = "divyadua"

        Class.forName(driver)
        val connection: Connection = DriverManager.getConnection(url, username, password)
        val statement: Statement = connection.createStatement()

        val query = "SELECT * FROM user_table"
        val result: ResultSet = statement.executeQuery(query)

        while (!isStopped && result.next()) {
          val value = result.getString("name")
          store(value)
        }

        result.close()
        statement.close()
        connection.close()

        restart("Trying to connect again")

      } catch {
        case e: ClassNotFoundException => restart("Class not found error " + e)
        case e: SQLException => restart("SQL exception" + e)
        case e: java.net.ConnectException => restart("Error connecting to " + e)
        case t: Throwable => restart("Error receiving data", t)
      }
    }
  }

  override def onStop(): Unit = {
  }

}
