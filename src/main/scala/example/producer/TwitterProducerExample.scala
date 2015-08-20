package example.producer

import twitter4j.Status
import twitter4j.json.DataObjectFactory
import play.api.libs.json.Json
import example.utils.TwitterClient

object TwitterProducer {
  def main(args: Array[String]): Unit = {
    val topicName =
      if(args.length == 0) "mali"
      else args(0)

    val strProducer = Producer[String](topicName)
    val twitterClient = new TwitterClient


    def handler(status: Status) = {
      val data = DataObjectFactory.getRawJSON(status)
      val tweet = Json.parse(data) \ "text"
      strProducer.send(tweet.toString)
    }

    twitterClient.addListener(handler)
    twitterClient.run
 }
}
