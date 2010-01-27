package twitterift.model

import twitterift.util._
import scala.xml.{NodeSeq,Text,Node,Elem}
import net.liftweb.util.{Box,Full,Empty,Helpers,Log}
import net.liftweb.util.Helpers._

import net.liftweb._
import http._
import util._
import Helpers._

import js._
import JsCmds._
import JE._


object TwitterUser {
  def apply(xml: Node) = new TwitterUser(
      (xml \ "id").text, 
      (xml \ "screen_name").text, 
      Integer.parseInt((xml \ "statuses_count").text), 
      (xml \ "profile_image_url").text,
      Integer.parseInt((xml \ "followers_count").text)
    )
}

case class TwitterUser (id: String, 
                        username: String,
                        statuses: Integer,
                        imgUrl: String,
                        followers: Integer
) {
  def toJs: JsExp = {
     JsObj(
       "id" -> id,
 	     "username" -> username,
 	     "statuses" -> statuses.toString,
 	     "imgUrl" -> imgUrl,
 	     "followers" -> followers.toString
 	  )
  }  
}