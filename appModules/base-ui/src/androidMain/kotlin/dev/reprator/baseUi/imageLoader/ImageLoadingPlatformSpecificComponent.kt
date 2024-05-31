package dev.reprator.baseUi.imageLoader

import android.app.Application
import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides

actual interface ImageLoadingPlatformSpecificComponent {
    @Provides
    fun providePlatformContext(application: Application): PlatformContext = application
}
