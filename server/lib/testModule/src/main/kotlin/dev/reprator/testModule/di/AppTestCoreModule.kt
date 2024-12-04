package dev.reprator.testModule.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.reprator.base.action.AppDatabaseFactory
import dev.reprator.base.action.JwtTokenService
import dev.reprator.commonFeatureImpl.di.APP_JWT_TOKEN_ACCESS
import dev.reprator.commonFeatureImpl.di.APP_JWT_TOKEN_REFRESH
import dev.reprator.commonFeatureImpl.di.APP_PLUGIN_CUSTOM_LIST
import dev.reprator.commonFeatureImpl.di.APP_RUNNING_PORT_ADDRESS
import dev.reprator.commonFeatureImpl.imp.DefaultDatabaseFactory
import dev.reprator.testModule.plugin.pluginClientResponseTransformation
import io.ktor.client.plugins.api.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.koin.core.Koin
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.dsl.module
import java.util.*

@Module
class AppTestCoreModule {

    @Single
    @Named(APP_RUNNING_PORT_ADDRESS)
    fun runningPortAddress(): Int {
        return ApplicationConfig("application-test.conf").property("ktor.deployment.port").getString().toInt()
    }

    @Single
    @Named(APP_PLUGIN_CUSTOM_LIST)
    fun clientPluginList(koin: Koin): List<ClientPlugin<Unit>> {
        return listOf(pluginClientResponseTransformation(koin))
    }
}

@Module
class JWTOverrideModule {

    @Single
    @Named(APP_JWT_TOKEN_ACCESS)
    fun jwtAccessToken(): Long = 2 * JwtTokenService.SECOND_1

    @Single
    @Named(APP_JWT_TOKEN_REFRESH)
    fun jwtRefreshToken(koin: Koin): Long = 3 * JwtTokenService.SECOND_1

}


@Module
public class AppDBModule {

    @Factory
    fun hikariConfig(): HikariConfig = HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        username = "root"
        password = "password"
        jdbcUrl = "jdbc:h2:mem:db${UUID.randomUUID()};MODE=MYSQL"
        maximumPoolSize = 2
        isAutoCommit = true
        validate()
    }

    @Factory
    fun dbCallBack(): (HikariDataSource, Database) -> Unit = { hikariDataSource, _ ->
        SchemaDefinition.createSchema(hikariDataSource)
    }

}

val testAppCommonDBModule = module {
    single<AppDatabaseFactory> {
        DefaultDatabaseFactory(get(), get())
    }
}