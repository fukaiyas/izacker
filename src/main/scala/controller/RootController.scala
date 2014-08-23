package controller

import model.Izakaya

class RootController extends ApplicationController {

  def index = {
    set("suggestion" -> chooseIzakaya())
    render("/root/index")
  }
  def suggestions = {
    render("/root/suggestions")
  }

  private def chooseIzakaya(): Option[Izakaya] = {
    scala.util.Random.shuffle(Izakaya.findAll()).headOption
  }

}

