package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class History(
  id: Long,
  izakaya: Long,
  date: LocalDate,
  createdAt: DateTime,
  updatedAt: DateTime
)

object History extends SkinnyCRUDMapper[History] with TimestampsFeature[History] {
  override lazy val tableName = "izakaya_histories"
  override lazy val defaultAlias = createAlias("h")

  override def extract(rs: WrappedResultSet, rn: ResultName[History]): History = new History(
    id = rs.get(rn.id),
    izakaya = rs.get(rn.izakaya),
    date = rs.get(rn.date),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
