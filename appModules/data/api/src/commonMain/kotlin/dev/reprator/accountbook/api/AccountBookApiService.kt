package dev.reprator.accountbook.api

import me.tatarka.inject.annotations.Inject

interface ApiService {
    suspend fun <T> splashData(): Result<T>
}

@Inject
class AccountBookApiService(): ApiService {

    companion object {
        const val END_POINT = "0.0.0.0:8081"
        private const val ENDPOINT_SPLASH = "splash"
    }

    @Inject
    override suspend fun <T> splashData(): Result<T> {
        throw Exception("error")
//        return httpClient.safeRequest<EntitySplash> {
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
