package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile

  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Constraints.schema ++ Users.schema

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Constraints
   *
   * @param userId         Database column user_id SqlType(int4)
   * @param pollutiontype  Database column pollutionType SqlType(varchar), Length(100,true)
   * @param specifiedvalue Database column specifiedValue SqlType(float8), Default(None)
   * @param lastdate       Database column lastDate SqlType(timestamptz), Default(None) */
  case class ConstraintsRow(userId: Int, pollutiontype: String, specifiedvalue: Option[Double] = None, lastdate: Option[java.sql.Timestamp] = None)

  /** GetResult implicit for fetching ConstraintsRow objects using plain SQL queries */
  implicit def GetResultConstraintsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Double]], e3: GR[Option[java.sql.Timestamp]]): GR[ConstraintsRow] = GR {
    prs =>
      import prs._
      ConstraintsRow.tupled((<<[Int], <<[String], <<?[Double], <<?[java.sql.Timestamp]))
  }

  /** Table description of table Constraints. Objects of this class serve as prototypes for rows in queries. */
  class Constraints(_tableTag: Tag) extends profile.api.Table[ConstraintsRow](_tableTag, "Constraints") {
    def * = (userId, pollutiontype, specifiedvalue, lastdate) <> (ConstraintsRow.tupled, ConstraintsRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userId), Rep.Some(pollutiontype), specifiedvalue, lastdate)).shaped.<>({ r => import r._; _1.map(_ => ConstraintsRow.tupled((_1.get, _2.get, _3, _4))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_id SqlType(int4) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column pollutionType SqlType(varchar), Length(100,true) */
    val pollutiontype: Rep[String] = column[String]("pollutionType", O.Length(100, varying = true))
    /** Database column specifiedValue SqlType(float8), Default(None) */
    val specifiedvalue: Rep[Option[Double]] = column[Option[Double]]("specifiedValue", O.Default(None))
    /** Database column lastDate SqlType(timestamptz), Default(None) */
    val lastdate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("lastDate", O.Default(None))

    /** Foreign key referencing Users (database name constraints_users_id_fk) */
    lazy val usersFk = foreignKey("constraints_users_id_fk", userId, Users)(r => r.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)
  }

  /** Collection-like TableQuery object for table Constraints */
  lazy val Constraints = new TableQuery(tag => new Constraints(tag))

  /** Entity class storing rows of table Users
   *
   * @param id       Database column id SqlType(serial), AutoInc, PrimaryKey
   * @param email    Database column email SqlType(varchar), Length(100,true)
   * @param password Database column password SqlType(varchar), Length(200,true)
   * @param lon      Database column lon SqlType(float8), Default(None)
   * @param lat      Database column lat SqlType(float8), Default(None) */
  case class UsersRow(id: Int, email: String, password: String, lon: Option[Double] = None, lat: Option[Double] = None)

  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Double]]): GR[UsersRow] = GR {
    prs =>
      import prs._
      UsersRow.tupled((<<[Int], <<[String], <<[String], <<?[Double], <<?[Double]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, email, password, lon, lat) <> (UsersRow.tupled, UsersRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(email), Rep.Some(password), lon, lat)).shaped.<>({ r => import r._; _1.map(_ => UsersRow.tupled((_1.get, _2.get, _3.get, _4, _5))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column email SqlType(varchar), Length(100,true) */
    val email: Rep[String] = column[String]("email", O.Length(100, varying = true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200, varying = true))
    /** Database column lon SqlType(float8), Default(None) */
    val lon: Rep[Option[Double]] = column[Option[Double]]("lon", O.Default(None))
    /** Database column lat SqlType(float8), Default(None) */
    val lat: Rep[Option[Double]] = column[Option[Double]]("lat", O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
