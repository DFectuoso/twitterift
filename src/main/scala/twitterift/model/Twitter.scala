package twitterift.model

import twitterift.util._
import _root_.scala.xml.{NodeSeq,Text,Node,Elem}
import _root_.net.liftweb.util.{Box,Full,Empty,Helpers,Log}
import _root_.net.liftweb.util.Helpers._

object Twitter {
  private val urlPart1 = "http://twitter.com/"
    
  def getAvailableRequest():String = 
    (getFromTwitter("account/rate_limit_status.xml") \ "remaining-hits").text
  
  def getFromTwitter(urlBody: String): NodeSeq = XmlFetcher.get(urlPart1 + urlBody)

  def getFriends(username:String, cursor:String) : (Seq[TwitterUser],String) =  {
    var xml = getFromTwitter("statuses/friends/" + username + ".xml?cursor=" + cursor)
    ((xml \\ "user").map(TwitterUser.apply), (xml \ "next_cursor").text)    
  }

  def getDetails(username:String):Seq[TwitterUser] =  
    (getFromTwitter("users/show/" + username + ".xml") \\ "user").map(TwitterUser.apply)

}