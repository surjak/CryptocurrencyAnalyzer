package controllers

import anorm.{RowParser, SQL, SqlParser, ~}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}
import services.AirlyDriver
import models.DBControllerAnd
import slick.jdbc.JdbcProfile
import play.api.db._
import play.api.db.DBApi

import scala.concurrent.ExecutionContext

@Singleton
class AppController @Inject()(dbapi: DBApi,cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def app = Action { implicit req =>
    val email = req.session.get("email").getOrElse("zle")
    //    println(email)
    //    EmailSender.sendEmail("radek4ec@gmail.com", "Tutaj EmailAlert\nCos sie stalo");

    val dbController = new DBControllerAnd(dbapi.database("default"))
    val user = dbController.getUserByEmail("ala12@12.pl")

    val nearestStationId = AirlyDriver.getNearestMeasurements(user)
    println(nearestStationId)
    Ok(views.html.app())
  }

}