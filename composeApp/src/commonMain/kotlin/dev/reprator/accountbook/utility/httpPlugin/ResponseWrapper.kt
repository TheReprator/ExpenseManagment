package dev.reprator.accountbook.utility.httpPlugin

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ResultDTOResponse<T>(@SerialName("statusCode") val statusCode: Int, @SerialName("data") val data: T)

@Serializable
data class FailDTOResponse(@SerialName("statusCode") val statusCode: Int, @SerialName("error")  val statusMessage: String)