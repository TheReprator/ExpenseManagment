package dev.reprator.accountbook.splash.dataSource.remote

import dev.reprator.accountbook.splash.dataSource.remote.modal.EntitySplash
import me.tatarka.inject.annotations.Inject

@Inject
class ApiService() {

    companion object {
        const val END_POINT = "0.0.0.0:8081"
        private const val ENDPOINT_SPLASH = "splash"
    }

    @Inject
    suspend fun splashData(): Result<EntitySplash> {
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
