package dev.reprator.accountbook.language.data.repositoryImpl.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntityLanguageMain(
    @SerialName("statusCode")
    val statusCode: Int = -1,
    @SerialName("data")
    val data: List<EntityLanguage> = emptyList()
)

@Serializable
data class EntityLanguage(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = ""
)