package dev.reprator.baseUi.imageLoading

import android.app.Application
import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides

actual interface ApiPlatformContextComponent {
      @Provides
    fun providePlatformContext(application: Application): PlatformContext = application
}