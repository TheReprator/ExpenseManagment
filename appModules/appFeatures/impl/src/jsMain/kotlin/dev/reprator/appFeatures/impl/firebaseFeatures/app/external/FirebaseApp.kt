@file:JsModule("firebase/app")
@file:JsNonModule

package dev.reprator.appFeatures.impl.firebaseFeatures.app.external

external interface FirebaseOptions {
    val apiKey: String
    val authDomain: String?
    val databaseURL: String?
    val projectId: String?
    val storageBucket: String?
    val messagingSenderId: String?
    val appId : String
    val measurementId: String?
}

external interface FirebaseSetting {
    val name: String
    val automaticDataCollectionEnabled: Boolean
}


external fun initializeApp(options: Any, config: Any= definedExternally): FirebaseApp

external interface FirebaseApp {
    val options: FirebaseOptions
    val config: FirebaseSetting
}

external interface FirebaseError {
    var code: String
    var message: String
    var name: String
    var stack: String?
        get() = definedExternally
        set(value) = definedExternally
}