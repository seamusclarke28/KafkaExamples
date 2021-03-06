package example.producer

import play.api.libs.json.Json
import scala.util.Random

object JsonProducerExample {
  def main(args: Array[String]): Unit = {
    val topicName =
      if(args.length == 0) "jsonTopic"
      else args(0)

    val producer = Producer[String](topicName)
    val message = Message(2, "mobile", "Temperature")

    implicit val writes = Json.writes[Message]

    producer.send(Json.toJson(message).toString)
  }
}

// case class Message(userId: Int, source: String)

case class Message(userId: Int, source: String, sensorType: String)



