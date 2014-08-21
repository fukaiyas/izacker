package controller

import model.Izakaya
import skinny._
import scalikejdbc._

class RootController extends ApplicationController {

  def index = {
    val rec = Izakaya.findById(1L)
    set("suggestion" -> rec.get)
    render("/root/index")
  }
  def suggestions = {
    render("/root/suggestions")
  }
}

