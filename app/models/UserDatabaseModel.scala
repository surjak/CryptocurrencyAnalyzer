package models

import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}
import models.Tables._
import org.mindrot.jbcrypt.BCrypt

class UserDatabaseModel(db: Database)(implicit ec: ExecutionContext) {
  def validateLogin(email: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(userRow => userRow.email === email).result)
    println(email, password)
    matches.map(userRows => userRows.filter(userRow => BCrypt.checkpw(password, userRow.password)).nonEmpty)
  }

  def createUser(email: String, password: String): Future[Boolean] = {
    db.run(Users += UsersRow(-1, email, BCrypt.hashpw(password, BCrypt.gensalt()))).map(addCount => addCount > 0)
  }
}
