package models

case class User(id: Int, email: String, lon: Double, lat: Double)

case class UserJoinConstraint(lon: Double, lat: Double, pollutionType: String, specifiedValue: Double, lastDate: )
