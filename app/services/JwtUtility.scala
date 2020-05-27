package services

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}

class JwtUtility {
  val JwtSecretKey = "supersecretkey"
  val JwtSecretAlgo = "HS256"
  val KEY = "Email"

  def createToken(payload: String): String = {
    val header = JwtHeader(JwtSecretAlgo)
    val claimsSet = JwtClaimsSet(Map(KEY -> payload))

    JsonWebToken(header, claimsSet, JwtSecretKey)
  }

  def isValidToken(jwtToken: String): Boolean =
    JsonWebToken.validate(jwtToken, JwtSecretKey)

  def decodePayload(jwtToken: String): Option[String] =
    jwtToken match {
      case JsonWebToken(header, claimsSet, signature) => Option(claimsSet.asSimpleMap.get(KEY))
      case _ => None
    }
}

object JwtUtility extends JwtUtility