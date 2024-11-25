package dev.reprator.testModule

import dev.reprator.base.beans.APIS
import dev.reprator.base.beans.API_BASE_URL
import dev.reprator.base_ktor.api.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.util.*
import org.junit.jupiter.api.extension.*
import org.koin.core.Koin

class KtorServerExtension : BeforeEachCallback, AfterEachCallback {

    companion object {
        var TEST_SERVER: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> ?= null
    }

    override fun beforeEach(context: ExtensionContext?) {
        val appConfig = ApplicationConfig("application-test.conf")
        val env = applicationEnvironment {
            config = appConfig
        }
        TEST_SERVER = embeddedServer(Netty, env, configure = {
            connector {
                host = API_BASE_URL.INTERNAL_APP.value
                port = appConfig.property("ktor.deployment.port").getString().toInt()
            }
        }).start(false)
    }

    override fun afterEach(context: ExtensionContext?) {
        TEST_SERVER?.stop(100, 100)
    }
}

suspend inline fun <reified T> hitApiWithClient(
    koin: Koin,
    fullUrl: String ="",
    methodName: HttpMethod = HttpMethod.Post,
    crossinline block: HttpRequestBuilder.() -> Unit ={}
) =
    koin.get<HttpClient>().safeRequest<T>(
        apiType = APIS.INTERNAL_APP,
        attributes = koin.get<Attributes>()
    ) {
        url {
            method = methodName
            path(fullUrl)
        }
        contentType(ContentType.Application.Json)
        block(this@safeRequest)
    }