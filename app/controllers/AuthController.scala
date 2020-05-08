package controllers

import javax.inject.Inject
import models.UserDatabaseModel
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Reads
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request, Result}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class AuthController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  private val model = new UserDatabaseModel(db)


  def createUser = Action.async { implicit req =>
    ???
  }
}
