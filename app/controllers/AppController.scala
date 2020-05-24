package controllers

import javax.inject.{Inject, Singleton}
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.AirlyDriver
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

@Singleton
class AppController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def configuration = Action { implicit req =>
    val email = req.session.get("email").getOrElse("zle")
    //    println(email)
    //    EmailSender.sendEmail("radek4ec@gmail.com", "Tutaj EmailAlert\nCos sie stalo");
    val obj = AirlyDriver.getParameterValue()

    Ok(views.html.configuration())
  }

  def loadUserLonAndLat = Action { implicit req =>
    val email = req.session.get("email")
    val json = req.body.asJson
    val lat = (json.get \ "lat").get.toString().toDouble
    val lon = (json.get \ "lon").get.toString().toDouble
    db.run(Users.filter(userRow => userRow.email === email).map(u => (u.lat, u.lon)).update(Option(lat), Option(lon)))
    Ok(Json.toJson("{status: 'ok'}"))
  }

}