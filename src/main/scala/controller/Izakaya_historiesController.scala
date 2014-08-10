package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.History

class Izakaya_historiesController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = History
  override def resourcesName = "izakaya_histories"
  override def resourceName = "history"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params).withDate("date")
  override def createForm = validation(createParams,
    paramKey("izakaya") is required & numeric & longValue,
    paramKey("date") is required & dateFormat
  )
  override def createFormStrongParameters = Seq(
    "izakaya" -> ParamType.Long,
    "date" -> ParamType.LocalDate
  )

  override def updateParams = Params(params).withDate("date")
  override def updateForm = validation(updateParams,
    paramKey("izakaya") is required & numeric & longValue,
    paramKey("date") is required & dateFormat
  )
  override def updateFormStrongParameters = Seq(
    "izakaya" -> ParamType.Long,
    "date" -> ParamType.LocalDate
  )

}
