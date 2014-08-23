package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    izakayaHistories.mount(ctx)
    izakayaMaster.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
    val suggestionUrl = get("/suggestions")(suggestions).as('suggestions)
  }
  object izakayaMaster extends _root_.controller.IzakayaMasterController with Routes {
  }
  object izakayaHistories extends _root_.controller.IzakayaHistoriesController with Routes {
  }

}
