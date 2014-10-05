package controller

import java.util.Date

import model.{ History, Izakaya }
import org.joda.time.{ DateTime, LocalDate }
import scalikejdbc._

class RootController extends ApplicationController {

  def index = {
    set("suggestion" -> suggestList().headOption)
    render("/root/index")
  }
  def suggestions = {
    set("suggestList" -> suggestList())
    render("/root/suggestions")
  }

  private def suggestList(): List[Izakaya] = {

    val last: Long = History.findAllWithLimitOffset(
      limit = 1,
      offset = 0,
      orderings = Seq(History.defaultAlias.date.desc)
    ).headOption.getOrElse(History(-1, -1, None, LocalDate.now, DateTime.now, DateTime.now)).izakayaId

    val recentPeriod = LocalDate.fromDateFields(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30 * 2))
    val weight: Map[Long, Int] = History.findAllBy(where = sqls"date >= $recentPeriod")
      .groupBy(_.izakayaId)
      .mapValues(_.size + 1)

    Izakaya.findAllBy(where = sqls"id <> $last")
      .sortBy(izakaya => izakaya.priority * 100 / weight.getOrElse(izakaya.id, 1))
      .reverse
  }
}

