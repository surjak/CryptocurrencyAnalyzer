package models

import java.util.Date

case class User(id: Int, email: String, lon: Double, lat: Double)

case class Location(lon: Double, lat: Double)

case class UserJoinConstraint(user_id: Int, email: String, lon: Double, lat: Double, pollutionType: String, specifiedValue: Double, lastDate: Date) //w razie czego dopiszemy

case class Measure(measureType: String, value: Double)