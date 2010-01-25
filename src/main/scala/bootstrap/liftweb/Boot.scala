package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("twitterift")
    
    LiftRules.rewrite.prepend(NamedPF("List") {
        case RewriteRequest(ParsePath("list" :: username :: Nil, _, _, _), _, _) =>
          RewriteResponse("list" :: Nil, Map("username" -> username ))
    })
  }
}

