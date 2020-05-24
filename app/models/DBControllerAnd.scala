package models

import anorm._

import play.api.db._




class DBControllerAnd (db: Database){

  def userParser: RowParser[User] = (
          SqlParser.get[Int]("users.id") ~
          SqlParser.get[String]("users.email") ~
          SqlParser.get[Double]("users.lon") ~
          SqlParser.get[Double]("users.lat")
    ) map {
    case columnvalue1 ~ columnvalue2 ~ columnvalue3 ~ columnvalue4 => // etc...
      User(columnvalue1, columnvalue2, columnvalue3, columnvalue4) // etc...
  }

  def getUserByEmail(email: String) = db.withConnection { implicit c =>
    val q = SQL(s"select * from users where email = '$email'")
    q.as(userParser.single)
  }
}



