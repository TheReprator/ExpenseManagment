package dev.reprator.accountbook

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.reprator.commonFeatureImpl.di.APP_RUNNING_PORT_ADDRESS
import dev.reprator.commonFeatureImpl.di.JWT_SERVICE
import dev.reprator.userIdentity.data.UserIdentityRepository
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.koin.core.qualifier.named
import org.koin.dsl.module
import dev.reprator.base_ktor.util.propertyConfig

//fun koinAppLateInitializationModule() = module {
//
//    single<(Int) -> Boolean>(named(JWT_SERVICE)) {
//        val isUserValid:(Int) -> Boolean = {
//            runBlocking {
//                val userController = get<UserIdentityRepository>()
//                try {
//                    val result = userController.getUserById(it)
//                    result.refreshToken.trim().isNotEmpty()
//                } catch (exception: Exception) {
//                    false
//                }
//            }
//        }
//        isUserValid
//    }
//}

//fun koinAppModule(applicationEnvironment: ApplicationEnvironment) = module {
//
//    single<ApplicationConfig> { applicationEnvironment.config }
//
//    factory<Int> (named(APP_RUNNING_PORT_ADDRESS)) {
//        ApplicationConfig("application.yaml").property("ktor.deployment.port").getString().toInt()
//    }
//
//}
//
//fun koinAppDBModule(callBack: (HikariDataSource, Database) -> Unit) = module {
//    single<HikariConfig> {
//
//        fun property(property: String): String {
//            val config = get<ApplicationConfig>()
//            return config.propertyConfig("storage.$property")
//        }
//
//        val appJdbcUrl =
//            "jdbc:postgresql://${property("serverName")}:${property("portNumber").toInt()}/${property("databaseName")}?user=${
//                property("userName")
//            }&password=${property("password")}"
//
//
//        HikariConfig().apply {
//            jdbcUrl = appJdbcUrl
//            maximumPoolSize = 3
//            isAutoCommit = false
//            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
//            driverClassName = property("driverClassName")
//            validate()
//        }
//
//    }
//
//    single<(HikariDataSource, Database) -> Unit> { callBack }
//}