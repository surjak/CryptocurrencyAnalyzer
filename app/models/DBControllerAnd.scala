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

  def userJoinConstraint: RowParser[UserJoinConstraint] = (
//    SqlParser.get[Int]("users.id") ~
      SqlParser.get[String]("users.email") ~
      SqlParser.get[Double]("lat") ~
      SqlParser.get[Double]("lon") ~
      SqlParser.get[String]("pollutionType") ~
        SqlParser.get[Double]("specifiedValue")
    ) map {
    case columnvalue1 ~ columnvalue2 ~ columnvalue3 ~ columnvalue4 ~ columnvalue5 => // etc...
      UserJoinConstraint(columnvalue1, columnvalue2, columnvalue3, columnvalue4, columnvalue5) // etc...
  }

  def getUserByEmail(email: String) = db.withConnection { implicit c =>
    val q = SQL(s"select * from users where email = '$email'")
    q.as(userParser.single)
  }

  def getUserJoinConstraint(): List[UserJoinConstraint] = db.withConnection{ implicit c =>
    val q = SQL("select email, lon, lat, \"pollutionType\", \"specifiedValue\" from users join \"Constraints\" C on users.id = C.user_id where users.lon is not null and users.lat is not null")
    q.as(userJoinConstraint.*)
  }

}



