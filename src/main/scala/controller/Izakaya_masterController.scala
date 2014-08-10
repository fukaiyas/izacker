package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Izakaya

class Izakaya_masterController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Izakaya
  override def resourcesName = "izakaya_master"
  override def resourceName = "izakaya"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("category") is required & maxLength(512),
    paramKey("priority") is required & numeric & longValue,
    paramKey("address") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "category" -> ParamType.String,
    "priority" -> ParamType.Long,
    "address" -> ParamType.String
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("category") is required & maxLength(512),
    paramKey("priority") is required & numeric & longValue,
    paramKey("address") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "category" -> ParamType.String,
    "priority" -> ParamType.Long,
    "address" -> ParamType.String
  )

}
