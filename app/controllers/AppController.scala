package controllers

import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class AppController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def app = Action { implicit req =>
    Ok(views.html.app())
  }

}