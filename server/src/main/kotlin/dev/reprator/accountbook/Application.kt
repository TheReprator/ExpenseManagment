package dev.reprator.accountbook

import dev.reprator.accountbook.di.AppModule
import dev.reprator.base.action.AppDatabaseFactory
import dev.reprator.commonFeatureImpl.di.commonFeatureCollectionModule
import dev.reprator.commonFeatureImpl.setupServerPlugin
import dev.reprator.country.controller.routeCountry
import dev.reprator.country.setUpKoinCountry
import dev.reprator.language.controller.routeLanguage
import dev.reprator.language.setUpKoinLanguage
import dev.reprator.splash.controller.routeSplash
import dev.reprator.userIdentity.controller.routeUserIdentity
import dev.reprator.userIdentity.setUpKoinUserIdentityModule
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger
import org.koin.ktor.plugin.KoinIsolated
import org.koin.ksp.generated.*
import org.koin.ktor.ext.inject


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.configureDI(): Module {
    install(KoinIsolated)
    val providedDependency = org.koin.dsl.module(createdAtStart = true) {
        single { environment } bind ApplicationEnvironment::class
    }
    return providedDependency
}

fun Application.module() {

    install(Koin) {
        SLF4JLogger()

        modules(configureDI(), AppModule().module)
//        modules(koinAppModule(this@module.environment), koinAppCommonModule1(this@module.environment.config),
//            koinAppLateInitializationModule(), koinAppDBModule { _, database ->
//                transaction(database) {
//                    SchemaUtils.createMissingTablesAndColumns(TableLanguage, TableCountry, TableUserIdentity)
//                }
//            }, koinAppCommonDBModule, koinAppNetworkClientModule
//        )

        commonFeatureCollectionModule()
        setUpKoinLanguage()
        setUpKoinCountry()
        setUpKoinUserIdentityModule()
    }


    val dbInstance by inject<AppDatabaseFactory>()
    dbInstance.connect()

    configureServerMonitoring()
    setupServerPlugin()


    routing {
        routeCountry()
        routeLanguage()
        routeSplash()
        routeUserIdentity()
    }
}
