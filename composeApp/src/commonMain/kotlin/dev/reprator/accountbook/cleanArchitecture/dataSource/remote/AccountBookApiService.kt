package dev.reprator.accountbook.cleanArchitecture.dataSource.remote

import dev.reprator.accountbook.cleanArchitecture.dataSource.remote.modal.EntitySplash
import dev.reprator.accountbook.utility.httpPlugin.ResultDTOResponse
import dev.reprator.accountbook.utility.httpPlugin.safeRequest
import io.ktor.client.*
import io.ktor.http.*
import me.tatarka.inject.annotations.Inject

@Inject
class ApiService(private val httpClient: HttpClient) {

    companion object {
        private const val END_POINT = "https://api.quotable.io/"
        private const val ENDPOINT_SPLASH = "splash"
    }

    @Inject
    suspend fun splashData(): Result<ResultDTOResponse<EntitySplash>> {
        return httpClient.safeRequest<ResultDTOResponse<EntitySplash>> {
            url {
                method = HttpMethod.Get
                path("$END_POINT$ENDPOINT_SPLASH")
            }
            contentType(ContentType.Application.Json)
        }
    }
}


/*
{
    "statusCode": 200,
    "data": {
        "imageList": [
            "/Users/vikramsingh/Downloads/AccountBook/KMP/server/splashFileDirectory/one.png"
        ],
        "languageList": [
            {
                "type": "LanguageModal$DTO",
                "id": 9,
                "name": "English"
            }
        ]
    }
}
*/
