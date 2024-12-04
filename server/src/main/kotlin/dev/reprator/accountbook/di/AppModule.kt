package dev.reprator.accountbook.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.reprator.base_ktor.util.propertyConfig
import dev.reprator.commonFeatureImpl.di.APP_RUNNING_PORT_ADDRESS
import dev.reprator.commonFeatureImpl.di.JWT_SERVICE
import dev.reprator.country.data.TableCountry
import dev.reprator.language.data.TableLanguage
import dev.reprator.userIdentity.data.TableUserIdentity
import dev.reprator.userIdentity.data.UserIdentityRepository
import io.ktor.server.config.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.*

@Module
@ComponentScan("dev.reprator.accountbook")
class AppModule {

    @Factory
    @Named(APP_RUNNING_PORT_ADDRESS)
    fun appPortAddress() = ApplicationConfig("application.yaml").property("ktor.deployment.port").getString().toInt()

    @Factory
    @Named(JWT_SERVICE)
    fun jwtService(userController: UserIdentityRepository): (Int) -> Boolean {
        val isUserValid: (Int) -> Boolean = {
            runBlocking {
                try {
                    val result = userController.getUserById(it)
                    result.refreshToken.trim().isNotEmpty()
                } catch (exception: Exception) {
                    false
                }
            }
        }
        return isUserValid
    }

    @Factory
    fun dbHikariConfig(config: ApplicationConfig): HikariConfig {
        fun property(property: String): String {
            return config.propertyConfig("storage.$property")
        }

        val appJdbcUrl =
            "jdbc:postgresql://${property("serverName")}:${property("portNumber").toInt()}/${property("databaseName")}?user=${
                property("userName")
            }&password=${property("password")}"


        val hikariConfig = HikariConfig().apply {
            jdbcUrl = appJdbcUrl
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            driverClassName = property("driverClassName")
            validate()
        }

        return hikariConfig
    }

    @Factory
    fun dbCallback(): (HikariDataSource, Database) -> Unit {
        val call: (HikariDataSource, Database) -> Unit = { _, db ->

            transaction(db) {
                SchemaUtils.createMissingTablesAndColumns(TableLanguage, TableCountry, TableUserIdentity)
            }
        }

        return call
    }
}
