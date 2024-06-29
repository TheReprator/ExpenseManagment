package dev.reprator.appFeatures.api.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppSuccessModal<T>(
    @SerialName("statusCode")
    val statusCode: Int = -1,
    @SerialName("data") val data: T
) 