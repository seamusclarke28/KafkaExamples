package example.producer

//import play.api.libs.json.Json
import play.api.libs.json._
import scala.util.Random

object JsonProducerStreamExample {
  def main(args: Array[String]): Unit = {
    val topicName =
      if (args.length == 0) "jsonTopic"
      else args(0)

    val baseJsonMessage: JsValue = Json.parse("""
          { "metric": "sys.cpu.nice", 
            "timestamp": 1346846400, 
            "value": 18, 
            "tags": { "host": "web01", "dc": "lga"}}
    """)
    
    

    //   val producer = Producer[String](topicName)
    //   val message = JsonMessage(2, "mobile", "Temperature")

    val strProducer = Producer[String](topicName)
    //  val jsonMessage = JsonMessage(2, "mobile", "Temperature")

    //   implicit val writes = Json.writes[JsonMessage]
    // implicit val formattedJson = Json.format[JsonMessage]
    
    val startTime = System.nanoTime

    val messageStream = Stream.continually {
      Json.stringify(baseJsonMessage)
    }.take(100)

    strProducer.sendStream(messageStream)
    
      println("time taken to send 100 messages : " + (System.nanoTime-startTime)/1e6+"ms")


    //    producer.send(Json.toJson(jsonMessage).toString)
  }
}

// case class Message(userId: Int, source: String)

case class JsonMessage(userId: Int, source: String, sensorType: String)


