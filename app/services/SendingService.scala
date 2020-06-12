package services

import models.DBControllerAnd
import play.api.db.Database

class SendingService(db : Database) extends Thread{
  var delay = 1000 * 10
  override def run(): Unit = {

          val dbController = new DBControllerAnd(db)

          AirlyDriver.checkUsers(dbController.getUserJoinConstraint())
          println("Emails has been sent...")

    }

}
