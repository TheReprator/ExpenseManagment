package dev.reprator.appFeatures.impl.api.plugins

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

fun HttpClientConfig<*>.pluginClientDefaultRequest() {

    defaultRequest {

        headers {
            append(HttpHeaders.ContentType, "application/json")
        }
    }
}
