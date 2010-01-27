package twitterift.comet

import scala.actors._
import scala.actors.Actor._
import net.liftweb.http._
import net.liftweb._
import mapper._
import S._
import SHtml._
import util._
import Helpers._
import js._
import JsCmds._
import JE._
import js.jquery._
import JqJsCmds._
import JqJE._


import twitterift.model._

class ListActor extends CometActor {  
  override def defaultPrefix = Full("list")

  override def localSetup {
    this ! Available
    this ! Request("-1")
    super.localSetup
  }
  
  override def localShutdown {
    super.localShutdown
  }
  
  
  def render =  {
    <span>
      <div id="messages">
      </div>
      <div id="who">
      </div> 
      { Script(JsRaw("var user = new Array();;")) }
    </span>
  }

  override def lowPriority = {
    case Request(point) =>{
        println("pidiendo que me den uno con pointer en:" + point + " para el user: " + name.open_!)
        var (seq,pointer) = Twitter.getFriends(name.open_!,point)
        if(pointer != "0"){
          this ! Request(pointer)
        }
        this ! TwitterSeq(seq)          
      } 
    case Available => {
        val available = Twitter.getAvailableRequest
        println("WE HAVE AVAILABLE:"+ available)
      }
    case TwitterSeq(seq) => {
        this ! Available
        var images = seq.map((u)=> <img id={u.id} src={u.imgUrl}/>)
        var userArray = seq.map((u)=> JsRaw("user[\""+u.id+"\"] = eval(" + u.toJs +")"))
        partialUpdate(AppendHtml("who", {images}) & userArray.foldLeft(Noop)(_&_) & JsRaw("funWithTwitter();"))
      }
  }

  override def handleJson(in: Any): JsCmd = {
    in match {
      case JsonCmd(_,_,d,_) => println("WTF BBQ JSON WHAT?" + d); Noop
    }
  }
}

case class Available
case class Request(pointer:String)
case class TwitterSeq(seq:Seq[TwitterUser])