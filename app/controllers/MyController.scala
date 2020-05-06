package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class MyController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index(id: Int) = Action {
    Ok(id.toString)
  }

}
