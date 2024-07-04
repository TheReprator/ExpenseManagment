package dev.reprator.baseUi.imageLoading

import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides

actual interface ImageLoadingPlatformContextComponent {
    @Provides
    fun providePlatformContext(): PlatformContext = PlatformContext.INSTANCE
}