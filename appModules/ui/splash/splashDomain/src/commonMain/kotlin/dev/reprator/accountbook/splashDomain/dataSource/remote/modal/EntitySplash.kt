package dev.reprator.accountbook.splashDomain.dataSource.remote.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntitySplash(
    @SerialName("imageList")
    val imageList: List<String> = emptyList(),
    @SerialName("languageList")
    val languageList: List<EntityLanguage> = emptyList()
)

@Serializable
data class EntityLanguage(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = ""
)