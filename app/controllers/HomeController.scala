package controllers
import play.api.Logger
import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def index(mess: String = "INDEX") = Action { implicit req => {

    if(req.session.get("email") != None) {
      Ok(views.html.index("Welcome to Email Alert.", "CONNECTED"))
    }
    else{
      Ok(views.html.index("Welcome to Email Alert.", mess))
    }
  }
    }

}
