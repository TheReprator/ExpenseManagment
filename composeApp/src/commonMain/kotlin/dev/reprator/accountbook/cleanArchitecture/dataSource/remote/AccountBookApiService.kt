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
        const val END_POINT = "0.0.0.0:8081"
        private const val ENDPOINT_SPLASH = "splash"
    }

    @Inject
    suspend fun splashData(): Result<EntitySplash> {
        return httpClient.safeRequest<EntitySplash> {
            url {
                method = HttpMethod.Get
                path(ENDPOINT_SPLASH)
            }
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
