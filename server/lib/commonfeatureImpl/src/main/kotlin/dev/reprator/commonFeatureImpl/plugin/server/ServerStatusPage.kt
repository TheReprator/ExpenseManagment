package dev.reprator.commonFeatureImpl.plugin.server

import dev.reprator.base.beans.ERROR_DESCRIPTION_NOT_FOUND
import dev.reprator.base.beans.ERROR_DESCRIPTION_UNKNOWN
import dev.reprator.base.usecase.FailDTOResponse
import dev.reprator.base_ktor.exception.StatusCodeException
import dev.reprator.base_ktor.util.respondWithError
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*

fun Application.configureStatusPage() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val errorResponse = if (cause is StatusCodeException)
                FailDTOResponse(cause.statusCode.value, cause.message.orEmpty())
            else
                FailDTOResponse(HttpStatusCode.InternalServerError.value, "500: ${cause.message}")

            call.respondWithError(errorResponse)
        }

        status(HttpStatusCode.NotFound, HttpStatusCode.Forbidden) { call, status ->
            val message = when (status) {
                HttpStatusCode.NotFound -> ERROR_DESCRIPTION_NOT_FOUND
                else -> ERROR_DESCRIPTION_UNKNOWN
            }
            call.respondWithError(FailDTOResponse(status.value, message))
        }
    }
}
