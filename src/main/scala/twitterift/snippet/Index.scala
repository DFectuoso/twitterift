package twitterift.snippet

import _root_.scala.xml.{NodeSeq,Text,Node,Elem}
import _root_.net.liftweb.util.{Box,Full,Empty,Helpers,Log}
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.http.{S,SHtml}
import twitterift.model._

class IndexSnippet {
  def render(xhtml:NodeSeq):NodeSeq = {
    var tempValue: String = "" 
    bind("form", xhtml,
      "username" -> SHtml.text(tempValue, tempValue = _),
      "submit" -> SHtml.submit("Submit", () => S.redirectTo("list/" + tempValue))      
    )
  }
  
  def friends(xhtml:NodeSeq):NodeSeq = {
    S.param("username") match{
      case Full(name) =>
        <lift:comet type="ListActor" name={name} />
      case _ =>
        S.redirectTo("/")
    }
  }
}