package dev.reprator.accountbook.utility.httpPlugin

import io.ktor.client.plugins.api.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

val DataTransformationPlugin = createClientPlugin("DataTransformationPlugin") {
  transformResponseBody { response, _, requestedType ->
 
            fun responseParent(): suspend () -> ResultDTOResponse<*>? = {
                try {
                    val t = requestedType.kotlinType
                    print(requestedType)
                    val body = Json.decodeFromString<ResultDTOResponse<*>>(response.bodyAsText())
                    body
                } catch (e: Exception) {
                    println()
                    println()
                    println()
                    e.printStackTrace()
                    
                    println()
                    println()
                    
                    println("PluginClientResponseTransformation localizedMessage ${e.message}")
                    null
                }
            }

            if (response.status.isSuccess()) {
                val ip = responseParent()()?.data
                ip
            } else {
                response.bodyAsText()
            }
        }
}