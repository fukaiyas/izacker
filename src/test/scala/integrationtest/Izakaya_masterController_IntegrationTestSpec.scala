package integrationtest

import org.scalatra.test.scalatest._
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class Izakaya_masterController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.izakayaMaster, "/*")

  override def afterAll() {
    Izakaya.deleteAll()
  }

  def newIzakaya = FactoryGirl(Izakaya).create()

  it should "show izakaya master" in {
    get("/izakaya_master") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/izakaya_master/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/izakaya_master.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/izakaya_master.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a izakaya in detail" in {
    get(s"/izakaya_master/${newIzakaya.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/izakaya_master/${newIzakaya.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/izakaya_master/${newIzakaya.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/izakaya_master/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a izakaya" in {
    post(s"/izakaya_master",
      "name" -> "dummy",
      "category" -> "dummy",
      "priority" -> Long.MaxValue.toString(),
      "address" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/izakaya_master",
        "name" -> "dummy",
        "category" -> "dummy",
        "priority" -> Long.MaxValue.toString(),
        "address" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Izakaya.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/izakaya_master/${newIzakaya.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a izakaya" in {
    put(s"/izakaya_master/${newIzakaya.id}",
      "name" -> "dummy",
      "category" -> "dummy",
      "priority" -> Long.MaxValue.toString(),
      "address" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/izakaya_master/${newIzakaya.id}",
        "name" -> "dummy",
        "category" -> "dummy",
        "priority" -> Long.MaxValue.toString(),
        "address" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a izakaya" in {
    delete(s"/izakaya_master/${newIzakaya.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/izakaya_master/${newIzakaya.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
