package controller

import skinny._
import skinny.validator._
import model.History

class IzakayaHistoriesController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = History
  override def resourcesName = "izakayaHistories"
  override def resourceName = "history"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params).withDate("date")
  override def createForm = validation(createParams,
    paramKey("izakaya_id") is required & numeric & longValue,
    paramKey("date") is required & dateFormat
  )
  override def createFormStrongParameters = Seq(
    "izakaya_id" -> ParamType.Long,
    "date" -> ParamType.LocalDate
  )

  override def updateParams = Params(params).withDate("date")
  override def updateForm = validation(updateParams,
    paramKey("izakaya_id") is required & numeric & longValue,
    paramKey("date") is required & dateFormat
  )
  override def updateFormStrongParameters = Seq(
    "izakaya_id" -> ParamType.Long,
    "date" -> ParamType.LocalDate
  )

  override def findResources(pageSize: Int, pageNo: Int) = {
    logger.debug(pageSize + ":" + pageNo)
    val h = History.defaultAlias
    History.findAllWithPagination(
      pagination = Pagination.page(pageNo).per(pageSize),
      orderings = Seq(h.date.desc)
    )
  }
}
