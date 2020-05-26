package models

case class User(id: Int, email: String, lon: Double, lat: Double)

case class Location(lon: Double, lat: Double)

case class UserJoinConstraint(email: String, lon: Double, lat: Double, pollutionType: String, specifiedValue: Double) //w razie czego dopiszemy

case class Measure(measureType: String, value: Double)