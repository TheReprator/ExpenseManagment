package dev.reprator.appFeatures.impl.analytics

import dev.reprator.appFeatures.api.analytics.AppAnalytics
import dev.reprator.appFeatures.impl.firebaseFeatures.app.FirebaseModalOptions
import dev.reprator.appFeatures.impl.firebaseFeatures.app.external.FirebaseApp
import dev.reprator.appFeatures.impl.firebaseFeatures.app.external.initializeApp
import dev.reprator.appFeatures.impl.firebaseFeatures.app.toJson
import dev.reprator.core.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

actual interface AnalyticsPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideAccountBookFirebaseApp(bind: AccountBookFirebaseAnalytics): FirebaseApp {

        val firebaseOptions = FirebaseModalOptions(
            "1:363088178696:web:9c62966032f7fcb152abbd",
            "AIzaSyAO7jqiG9IWr18FQJRf3BJEiPSlfT8nNEM",
            "https://accountbook-ac59f.firebaseio.com",
            "accountbook-ac59f.appspot.com", "accountbook-ac59f",
            "363088178696", "accountbook-ac59f.firebaseapp.com", "G-KBKTTEQMRB"
        )

        //val setting = FirebaseModalSetting(automaticDataCollectionEnabled =  false)
        return initializeApp(firebaseOptions.toJson()/*, setting.toJson()*/)
    }

    @ApplicationScope
    @Provides
    fun provideAccountBookAppAnalytics(firebaseApp: AccountBookFirebaseAnalytics): AppAnalytics = firebaseApp

}