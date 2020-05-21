package services


import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}
import scalaj.http._
import models.Tables.UsersRow
import models.api.PollutionModel

object AirlyDriver {
  def getParameterValue() = {
    val response:  HttpResponse[String] = Http("https://airapi.airly.eu/v2/installations/100").
      header("content-type", "application/json").
      header("apikey", "xbZ9eEq0RqLd1ADXXEoHh1dBPoUc5Mh7").
      asString
    lazy val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.readValue[PollutionModel](response.body).additionalProperties.get("elevation")

  }
}


//object Appl extends App{
//  println("Ala")
//  //  AirlyDriver.getParameterValue()
//}

