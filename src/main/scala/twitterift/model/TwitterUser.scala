package twitterift.model

import twitterift.util._
import _root_.scala.xml.{NodeSeq,Text,Node,Elem}
import _root_.net.liftweb.util.{Box,Full,Empty,Helpers,Log}
import _root_.net.liftweb.util.Helpers._

object TwitterUser {
  def apply(xml: Node) = new TwitterUser(
      (xml \ "id").text, 
      (xml \ "screen_name").text, 
      (xml \ "profile_image_url").text,
      Integer.parseInt((xml \ "followers_count").text)
    )
}

case class TwitterUser (id: String, 
                        username: String,
                        imgUrl: String,
                        followers: Integer
)