package controllers

import javax.inject.{Inject, Singleton}
import models.Tables._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.db.DBApi
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._


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
class AppController @Inject()(dbapi: DBApi, cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  def configuration = Action { implicit req =>
    req.session.get("email").map(email =>
      Ok(views.html.configuration(ConstraintsForm.form, "Add constraint"))
    ).getOrElse(Redirect(routes.AuthController.login()).flashing("Login" -> "Login"))
    //    println(email)
    //    EmailSender.sendEmail("radek4ec@gmail.com", "Tutaj EmailAlert\nCos sie stalo");

    //    val dbController = new DBControllerAnd(dbapi.database("default"))
    //    val user = dbController.getUserByEmail("ala12@12.pl")

    //    val nearestStationId = AirlyDriver.getNearestMeasurements(user)

    //    val gowno = dbController.getUserJoinConstraint()

    //println(AirlyDriver.checkUsers(dbController.getUserJoinConstraint()))

    //    val obj = AirlyDriver.getParameterValue()

    //    Ok(views.html.configuration(ConstraintsForm.form, "Add constraint"))
  }

  def loadUserLonAndLat = Action.async { implicit req =>

    val json = req.body.asJson
    val lat = (json.get \ "lat").get.toString().toDouble
    val lon = (json.get \ "lon").get.toString().toDouble

    req.session.get("email").map(email =>
      db.run(Users.filter(userRow => userRow.email === email).map(u => (u.lat, u.lon)).update(Option(lat), Option(lon)))

    ).map(res => Future.successful(Ok(Json.toJson("{status: 'ok'}"))))
      .getOrElse(Future.successful(Redirect(routes.AuthController.login()).flashing("Login" -> "Login")))

  }

  def createConstraint = Action.async { implicit req =>
    ConstraintsForm.form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.configuration(formWithErrors, "Add constraint")))
      ,
      formdata => {
        req.session.get("email").map(email =>
          db.run(Users.filter(userRow => userRow.email === email).map(u => u.id).result)
        ).map(userId => userId.map(id => id.head).map(id =>
          db.run(Constraints += ConstraintsRow(id, formdata.pollutionType, Option(formdata.value)))))
          .map(res => Future.successful(Redirect(routes.AppController.createConstraint()).flashing("Saved" -> "Saved")))
          .getOrElse(Future.successful(Redirect(routes.AuthController.login()).flashing("Login" -> "Login")))
      }
    )

  }

  def showPanel = Action.async { implicit req =>
    req.session.get("email").map(email => db.run(Users.filter(userRow => userRow.email === email).map(u => u.id).result))
      .map(userId => userId.map(id => id.head).flatMap(id => db.run(Constraints.filter(c => c.userId === id).result).map(d => d.toList)))
      .map(data => data.map(d => Ok(views.html.panel(d))))
      .getOrElse(Future.successful(Redirect(routes.AuthController.login()).flashing("Login" -> "Login")))
  }

  def deleteConstraint = Action.async { implicit req =>

    val pollutionType = req.body.asFormUrlEncoded.get("pollutiontype").head
    req.session.get("email")
      .map(email => db.run(Users.filter(userRow => userRow.email === email).map(u => u.id).result))
      .map(userId => userId.map(id => id.head).flatMap(id => db.run(Constraints.filter(c => c.userId === id && c.pollutiontype === pollutionType).delete)).map(d =>
        Redirect(routes.AppController.showPanel()).flashing("Deleted" -> "Deleted")
      ))
      .getOrElse(Future.successful(Redirect(routes.AuthController.login()).flashing("Login" -> "Login")))
  }

}