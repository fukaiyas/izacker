package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class IzakayaHistoriesControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    History.deleteAll()
  }

  def createMockController = new IzakayaHistoriesController with MockController
  def newHistory = FactoryGirl(History).create()

  describe("izakayaHistoriesController") {

    describe("shows izakaya histories") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/izakayaHistories/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/izakayaHistories/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a history") {
      it("shows HTML response") {
        val history = newHistory
        val controller = createMockController
        controller.showResource(history.id)
        controller.status should equal(200)
        controller.requestScope[History]("item") should equal(Some(history))
        controller.renderCall.map(_.path) should equal(Some("/izakayaHistories/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/izakayaHistories/new"))
      }
    }

    describe("creates a history") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "izakaya" -> Long.MaxValue.toString(),
          "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()))
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be > (0)
      }
    }

    it("shows a resource edit input form") {
      val history = newHistory
      val controller = createMockController
      controller.editResource(history.id)
      controller.status should equal(200)
      controller.renderCall.map(_.path) should equal(Some("/izakayaHistories/edit"))
    }

    it("updates a history") {
      val history = newHistory
      val controller = createMockController
      controller.prepareParams(
        "izakaya" -> Long.MaxValue.toString(),
        "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()))
      controller.updateResource(history.id)
      controller.status should equal(200)
    }

    it("destroys a history") {
      val history = newHistory
      val controller = createMockController
      controller.destroyResource(history.id)
      controller.status should equal(200)
    }

  }

}
