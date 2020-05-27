package controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}
import services.AuthService
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class LoginForm(email: String, password: String)

object LoginForm {
  val form: Form[LoginForm] = Form(
    mapping("Email" -> email,
      "Password" -> text
    )(LoginForm.apply)(LoginForm.unapply)
  )
}

case class UserForm(email: String, password: String, confirmPassword: String)

object UserForm {
  val form: Form[UserForm] = Form(
    mapping(
      "Email" -> email,
      "Password" -> text(minLength = 6),
      "Confirm password" -> text
    )(UserForm.apply)(UserForm.unapply).verifying("Passwords do not match", data => data.password == data.confirmPassword)
  )
}


@Singleton
class AuthController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider)(implicit ex: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  private val model = new AuthService(db)


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
            Redirect(routes.AppController.configuration()).flashing("Register" -> "Registered!").withSession("email" -> formData.email)
          } else {
            Redirect(routes.AuthController.register()).flashing("userExists" -> "User already exists!")
          }
        )
      }
    )
  }

  def login = Action { implicit req =>
    Ok(views.html.login(LoginForm.form))
  }

  def logout = Action { implicit req =>
    Redirect(routes.HomeController.index()).withNewSession
  }

  def loginHandler = Action.async { implicit req =>
    LoginForm.form.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.login(formWithErrors)))
      ,
      formData => {
        model.validateLogin(formData.email, formData.password).map(data =>
          if (data) {
            Redirect(routes.AppController.configuration()).flashing("Login" -> "Login succeeded!").withSession("email" -> formData.email)
          } else {
            Redirect(routes.AuthController.login()).flashing("LoginFailed" -> "Login failed!")
          }
        )
      })
  }

}
