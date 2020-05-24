package services


import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}
import models.{DBControllerAnd, User}
import scalaj.http._
import play.api.libs.json._
import models.Tables.UsersRow
import models.api.PollutionModel

object AirlyDriver {
  def getNearestMeasurements(user: User) = {
    val response:  HttpResponse[String] = Http("https://airapi.airly.eu/v2/measurements/nearest").
      header("content-type", "application/json").
      header("apikey", "xbZ9eEq0RqLd1ADXXEoHh1dBPoUc5Mh7").
      param("lat", user.lat.toString).
      param("lng", user.lon.toString).
      param("maxDistanceKM","10").
      param("maxResults","1").
      asString

    val json: JsValue = Json.parse(response.body)

    (json \ "current" \ "values").get

  }

  def checkUsers
}


object Test extends App{
  println("Ala")
  //  AirlyDriver.getParameterValue()
}

