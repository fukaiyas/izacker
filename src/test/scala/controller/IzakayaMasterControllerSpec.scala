package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class IzakayaMasterControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    Izakaya.deleteAll()
  }

  def createMockController = new IzakayaMasterController with MockController
  def newIzakaya = FactoryGirl(Izakaya).create()

  describe("izakayaMasterController") {

    describe("shows izakaya master") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/izakayaMaster/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/izakayaMaster/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a izakaya") {
      it("shows HTML response") {
        val izakaya = newIzakaya
        val controller = createMockController
        controller.showResource(izakaya.id)
        controller.status should equal(200)
        controller.requestScope[Izakaya]("item") should equal(Some(izakaya))
        controller.renderCall.map(_.path) should equal(Some("/izakayaMaster/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/izakayaMaster/new"))
      }
    }

    describe("creates a izakaya") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "category" -> "dummy",
          "priority" -> Long.MaxValue.toString(),
          "address" -> "dummy")
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
      val izakaya = newIzakaya
      val controller = createMockController
      controller.editResource(izakaya.id)
      controller.status should equal(200)
      controller.renderCall.map(_.path) should equal(Some("/izakayaMaster/edit"))
    }

    it("updates a izakaya") {
      val izakaya = newIzakaya
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "category" -> "dummy",
        "priority" -> Long.MaxValue.toString(),
        "address" -> "dummy")
      controller.updateResource(izakaya.id)
      controller.status should equal(200)
    }

    it("destroys a izakaya") {
      val izakaya = newIzakaya
      val controller = createMockController
      controller.destroyResource(izakaya.id)
      controller.status should equal(200)
    }

  }

}
