package controllers

import anorm.{RowParser, SQL, SqlParser, ~}
import javax.inject.{Inject, Singleton}
import models.Tables._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}

import services.AirlyDriver
import models.DBControllerAnd
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import play.api.db._
import play.api.db.DBApi



//import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class ConstraintsForm(pollutionType: String, value: Double)

object ConstraintsForm {
  val form: Form[ConstraintsForm] = Form(mapping(
    "Pollution Type" -> text,
    "Value" -> of(doubleFormat)
  )(ConstraintsForm.apply)(ConstraintsForm.unapply))
}



@Singleton
class AppController @Inject()(dbapi: DBApi,cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def configuration = Action { implicit req =>
    val email = req.session.get("email").getOrElse("zle")
    //    println(email)
    //    EmailSender.sendEmail("radek4ec@gmail.com", "Tutaj EmailAlert\nCos sie stalo");

    val dbController = new DBControllerAnd(dbapi.database("default"))
    val user = dbController.getUserByEmail("ala12@12.pl")

//    val nearestStationId = AirlyDriver.getNearestMeasurements(user)

    val gowno = dbController.getUserJoinConstraint()

    println(AirlyDriver.checkUsers(dbController.getUserJoinConstraint()))

    //    val obj = AirlyDriver.getParameterValue()

    Ok(views.html.configuration(ConstraintsForm.form))
  }

  def loadUserLonAndLat = Action { implicit req =>
    val email = req.session.get("email")
    val json = req.body.asJson
    val lat = (json.get \ "lat").get.toString().toDouble
    val lon = (json.get \ "lon").get.toString().toDouble
    db.run(Users.filter(userRow => userRow.email === email).map(u => (u.lat, u.lon)).update(Option(lat), Option(lon)))
    Ok(Json.toJson("{status: 'ok'}"))
  }

  def createConstraint = Action.async { implicit req =>
    ConstraintsForm.form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.configuration(formWithErrors)))
      ,
      formdata => {
        val email = req.session.get("email")
        val userId = db.run(Users.filter(userRow => userRow.email === email).map(u => u.id).result)
        userId.map(id => id.head).map(id =>
          db.run(Constraints += ConstraintsRow(id, formdata.pollutionType, Option(formdata.value))))

        Future.successful(Redirect(routes.AppController.createConstraint()))
      }
    )

  }

}