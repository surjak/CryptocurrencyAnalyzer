package controllers

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

  //  private val model = new UserDatabaseModel(db)

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit req =>
    //    model.createUser("ala@gmail.com", "ala123").map(data => {
    //      if (!data) {
    //        Future.successful(Ok(views.html.index("Welcome to Cryptocurrency analyzer. Not found")))
    //      }
    //      else {
    //        Future.successful(Ok(views.html.index("Welcome to Cryptocurrency analyzer.Found")))
    //      }
    //
    //    })
    //    Future.successful(Ok(views.html.index("Welcome to Cryptocurrency analyzer.")))
    Ok(views.html.index("Welcome to Cryptocurrency analyzer."))
  }

}
