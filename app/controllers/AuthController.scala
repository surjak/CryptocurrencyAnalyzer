package controllers

import javax.inject.{Inject, Singleton}
import models.UserDatabaseModel
import play.api.data.Form
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Reads
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request, Result}
import slick.jdbc.JdbcProfile
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.data.validation.Constraints._
import scala.concurrent.{ExecutionContext, Future}

case class UserForm(email: String, password: String, confirmPassword: String)

object UserForm {
  val form: Form[UserForm] = Form(
    mapping(
      "email" -> email,
      "password" -> text(minLength = 6),
      "confirmPassword" -> text
    )(UserForm.apply)(UserForm.unapply).verifying("Passwords do not match", data => data.password == data.confirmPassword)
  )
}

@Singleton
class AuthController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  private val model = new UserDatabaseModel(db)

  def register = Action { implicit req =>
    Ok(views.html.register(UserForm.form))
  }

  def createUser = Action.async { implicit req =>
    UserForm.form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.register(formWithErrors)))
      ,
      formData => {

        model.createUser(formData.email, formData.password).map(data =>
          if (data) {
            Redirect(routes.HomeController.index())
          } else {
            BadRequest(views.html.register(UserForm.form))
          }
        )

      }
    )
    //    req.body.asFormUrlEncoded.map(data => {
    //      val email = data("email").head
    //      val password = data("password").head
    //      val confirmPassword = data("confirmPassword").head
    //      println(email, password, confirmPassword)
    //      Future.successful(Redirect(routes.AuthController.register()))
    //    }).getOrElse(Future.successful(Redirect(routes.AuthController.register())))
  }

}
