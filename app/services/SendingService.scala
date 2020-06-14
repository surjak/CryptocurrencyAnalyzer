package services

import models.DBControllerAnd
import play.api.db.Database

class SendingService(db : Database) extends Thread{
  var delay = 1000 * 60 * 60 * 12 // 12h
  override def run(): Unit = {

          val dbController = new DBControllerAnd(db)

          AirlyDriver.checkUsers(dbController.getUserJoinConstraint(),dbController)
          println("Emails has been sent...")

    }

}
