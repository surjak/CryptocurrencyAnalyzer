package services

import models.Tables._
import org.mindrot.jbcrypt.BCrypt
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class AuthService(db: Database)(implicit ec: ExecutionContext) {
  def validateLogin(email: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(userRow => userRow.email === email).result)
    matches.map(userRows => userRows.exists(userRow => BCrypt.checkpw(password, userRow.password)))
  }


  def createUser(email: String, password: String): Future[Boolean] = {
    checkIfExists(email).flatMap(result =>
      if (!result)
        db.run(Users += UsersRow(-1, email, BCrypt.hashpw(password, BCrypt.gensalt()))).map(addCount => addCount > 0)
      else
        Future.successful(false)
    )
  }

  def checkIfExists(email: String): Future[Boolean] = {
    db.run(Users.filter(userRow => userRow.email === email).exists.result)
  }
}
