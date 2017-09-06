name := "Spark-Streaming-Assignment"

version := "0.1"

scalaVersion := "2.11.8"

val sparkCore = "org.apache.spark" % "spark-core_2.11" % "2.2.0"
val sparkSQl = "org.apache.spark" % "spark-sql_2.11" % "2.2.0"
val sparkStream =  "org.apache.spark" % "spark-streaming_2.11" % "2.2.0"
val postgresDependency = "org.postgresql" % "postgresql" % "9.4-1200-jdbc41"

libraryDependencies ++= Seq(sparkCore, sparkSQl, sparkStream, postgresDependency)