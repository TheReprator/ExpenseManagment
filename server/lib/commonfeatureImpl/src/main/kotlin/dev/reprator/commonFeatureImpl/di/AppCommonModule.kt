package dev.reprator.commonFeatureImpl.di

import dev.reprator.base.action.JWTConfiguration
import dev.reprator.base.action.JwtTokenService
import dev.reprator.base.beans.APP_COROUTINE_SCOPE
import dev.reprator.base.beans.UPLOAD_FOLDER_SPLASH
import dev.reprator.base.beans.VERIFICATION_SMS_PHONE_APIKEY
import dev.reprator.base.beans.VERIFICATION_SMS_PHONE_USERID
import dev.reprator.base_ktor.util.propertyConfig
import dev.reprator.commonFeatureImpl.imp.JwtTokenServiceImpl
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.*

@Module
class AppCommonModule {

    @Factory
    fun applicationConfig(@Provided environment: ApplicationEnvironment): ApplicationConfig = environment.config

    @Factory
    @Named(UPLOAD_FOLDER_SPLASH)
    fun splashUploadFolderPath( config : ApplicationConfig) =  config.propertyConfig(UPLOAD_FOLDER_SPLASH)

    @Factory
    @Named(VERIFICATION_SMS_PHONE_APIKEY)
    fun keyApiSms( config : ApplicationConfig) =  config.propertyConfig(VERIFICATION_SMS_PHONE_APIKEY)

    @Factory
    @Named(VERIFICATION_SMS_PHONE_USERID)
    fun kePhoneUserId( config : ApplicationConfig) =  config.propertyConfig(VERIFICATION_SMS_PHONE_USERID)

    @Single
    @Named(APP_COROUTINE_SCOPE)
    fun appCoroutineScope( config : ApplicationConfig): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Factory
    @Named(KEY_SERVICE_SHUTDOWN)
    fun keyServiceShutDown( config : ApplicationConfig) =  config.propertyConfig("ktor.deployment.$KEY_SERVICE_SHUTDOWN.url")

    @Single
    @Named(JWT_SERVICE)
    fun jwtService() : (Int) -> Boolean =  { true }

    @Single
    @Named(APP_JWT_TOKEN_ACCESS)
    fun jwtAccessToken() : Long =   12 * JwtTokenService.HOUR_1_MILLISECONDS

    @Single
    @Named(APP_JWT_TOKEN_REFRESH)
    fun jwtRefreshToken() : Long =   24 * JwtTokenService.HOUR_1_MILLISECONDS


    @Single
    fun jwtTokenService(config : ApplicationConfig, @Named(APP_JWT_TOKEN_ACCESS) accessToken: Long,
                        @Named(APP_JWT_TOKEN_REFRESH) refreshToken: Long, @Named(JWT_SERVICE) isUserValid: (Int) -> Boolean) : JwtTokenService {

        val property: (String) -> String = {
            config.propertyConfig("jwt.$it")
        }
        
        val jwtConfiguration =
            JWTConfiguration(property("secret"), property("audience"), property("issuer"), property("realm"))

        return JwtTokenServiceImpl(jwtConfiguration, accessToken, refreshToken, isUserValid)
    }
}