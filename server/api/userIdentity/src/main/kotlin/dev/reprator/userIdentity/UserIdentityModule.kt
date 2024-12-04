package dev.reprator.userIdentity

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("dev.reprator.userIdentity")
class UserIdentityModule
/*
val userIdentityModule = module {
    factory<UserIdentityResponseRegisterMapper> { UserIdentityResponseRegisterMapper() }
    single<UserIdentityRepository> { UserIdentityRepositoryImpl(get(), get()) }

    single<SMScodeGenerator> { SMScodeGeneratorImpl(get(), get(), get()) }
    single<UserIdentityFacade> { UserIdentityFacadeImpl(get(), get(), get()) }

    single<UserIdentityController> { UserIdentityControllerImpl(get()) }
}*/
