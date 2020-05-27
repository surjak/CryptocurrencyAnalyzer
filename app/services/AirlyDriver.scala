package services


import models.{Location, Measure, UserJoinConstraint}
import play.api.libs.json._
import scalaj.http._

object AirlyDriver {
  def getNearestMeasurements(userLocation: Location) = {
    val response:  HttpResponse[String] = Http("https://airapi.airly.eu/v2/measurements/nearest").
      header("content-type", "application/json").
      header("apikey", "xbZ9eEq0RqLd1ADXXEoHh1dBPoUc5Mh7").
      param("lat", userLocation.lat.toString).
      param("lng", userLocation.lon.toString).
      param("maxDistanceKM","10").
      param("maxResults","1").
      asString

    val json: JsValue = Json.parse(response.body)

    (json \ "current" \ "values").get.as[JsArray].value.map(x => new Measure((x \ "name").as[String], (x \ "value").as[Double]))

  }

  def checkUsers(usersWithConstraints: List[UserJoinConstraint]) = {
    usersWithConstraints.foreach(userWithConstraints =>{
      if(checkUser(userWithConstraints)){
        //tu będzie trzeba wstawić wysylanie maila
        println(s"Wysylam mail do ${userWithConstraints.email} bo przekroczył: ${userWithConstraints.pollutionType}")
        EmailSender.sendEmail(userWithConstraints.email, s"${userWithConstraints.pollutionType} przekroczyło normę!")
      }
    })
  }

  def checkUser(userWithConstraint: UserJoinConstraint) = {
    val measures = getNearestMeasurements(Location(userWithConstraint.lat, userWithConstraint.lon))
    val valuesToCheck = measures.filter(_.measureType == userWithConstraint.pollutionType).map(measure => measure.value)
    if(valuesToCheck.length > 0){
      userWithConstraint.specifiedValue < valuesToCheck.apply(0)
    }
    else{
      false
    }
  }
}
