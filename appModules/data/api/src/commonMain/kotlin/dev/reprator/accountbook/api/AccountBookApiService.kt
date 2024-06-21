package dev.reprator.accountbook.api

import dev.reprator.accountbook.api.client.AppResult
import dev.reprator.accountbook.api.client.safeRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path
import me.tatarka.inject.annotations.Inject

interface ApiService {
    suspend fun <T> splashData(): AppResult<T>
}

@Inject
class AccountBookApiService(private val httpClient: HttpClient): ApiService {

    companion object {
        const val END_POINT = "0.0.0.0:8081"
        private const val ENDPOINT_SPLASH = "splash"
    }

    @Inject
    override suspend fun <T> splashData(): AppResult<T> {
        throw Exception("error")

//        val result =  httpClient.safeRequest<T> {
//            url {
//                method = HttpMethod.Get
//                path(ENDPOINT_SPLASH)
//            }
//        }
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
