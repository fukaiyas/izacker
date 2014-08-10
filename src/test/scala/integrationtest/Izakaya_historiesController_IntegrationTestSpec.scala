package integrationtest

import org.scalatra.test.scalatest._
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class Izakaya_historiesController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.izakayaHistories, "/*")

  override def afterAll() {
    History.deleteAll()
  }

  def newHistory = FactoryGirl(History).create()

  it should "show izakaya histories" in {
    get("/izakaya_histories") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/izakaya_histories/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/izakaya_histories.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/izakaya_histories.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a history in detail" in {
    get(s"/izakaya_histories/${newHistory.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/izakaya_histories/${newHistory.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/izakaya_histories/${newHistory.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/izakaya_histories/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a history" in {
    post(s"/izakaya_histories",
      "izakaya" -> Long.MaxValue.toString(),
      "date" -> skinny.util.DateTimeUtil.toString(new LocalDate())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/izakaya_histories",
        "izakaya" -> Long.MaxValue.toString(),
        "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        History.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/izakaya_histories/${newHistory.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a history" in {
    put(s"/izakaya_histories/${newHistory.id}",
      "izakaya" -> Long.MaxValue.toString(),
      "date" -> skinny.util.DateTimeUtil.toString(new LocalDate())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/izakaya_histories/${newHistory.id}",
        "izakaya" -> Long.MaxValue.toString(),
        "date" -> skinny.util.DateTimeUtil.toString(new LocalDate()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a history" in {
    delete(s"/izakaya_histories/${newHistory.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/izakaya_histories/${newHistory.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
