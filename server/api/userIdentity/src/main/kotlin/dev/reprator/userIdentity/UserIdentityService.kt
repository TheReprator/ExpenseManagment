package dev.reprator.userIdentity

import org.koin.core.KoinApplication
import org.koin.ksp.generated.*

fun KoinApplication.setUpKoinUserIdentityModule() {
    modules(UserIdentityModule().module)
}

