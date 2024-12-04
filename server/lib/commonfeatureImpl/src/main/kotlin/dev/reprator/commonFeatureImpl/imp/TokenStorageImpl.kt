package dev.reprator.commonFeatureImpl.imp

import dev.reprator.base.action.TokenStorage
import org.koin.core.annotation.Single

@Single
class TokenStorageImpl : TokenStorage {

    override var accessToken: String= ""
    override var refresh: String = ""

    override fun updateToken(accessToken: String, refresh: String) {
        this.accessToken = accessToken
        this.refresh = refresh
    }

    override fun cleaToken() {
        accessToken = ""
        refresh = ""
    }

    override fun clearAccessToken() {
        accessToken = ""
    }
}