package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class Izakaya(
  id: Long,
  name: String,
  category: String,
  priority: Long,
  address: String,
  createdAt: DateTime,
  updatedAt: DateTime)

object Izakaya extends SkinnyCRUDMapper[Izakaya] with TimestampsFeature[Izakaya] {
  override lazy val tableName = "izakaya_master"
  override lazy val defaultAlias = createAlias("i")

  override def extract(rs: WrappedResultSet, rn: ResultName[Izakaya]): Izakaya = new Izakaya(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    category = rs.get(rn.category),
    priority = rs.get(rn.priority),
    address = rs.get(rn.address),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )

}
