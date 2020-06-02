package services

import models.DBControllerAnd
import play.api.db.Database

class SendingService(db : Database) extends Thread{
  var delay = 1000 * 10
  override def run(): Unit = {
    while (true) {
      Thread.sleep(delay)
//      matches.map(constrRow => constrRow.exists(userRow => ))
//          println(email)
//          EmailSender.sendEmail("radek4ec@gmail.com", "Tutaj EmailAlert\nCos sie stalo");
//
          val dbController = new DBControllerAnd(db)
//          val user = dbController.getUserByEmail("ala12@12.pl")
//
//          val nearestStationId = AirlyDriver.getNearestMeasurements(user)
//
//          val gowno = dbController.getUserJoinConstraint()
//
          AirlyDriver.checkUsers(dbController.getUserJoinConstraint())
          println("Emails has been sent...")

//          val obj = AirlyDriver.getParameterValue()
//
//          Ok(views.html.configuration(ConstraintsForm.form, "Add constraint"))
    }
    println("End sending...")
  }

}
