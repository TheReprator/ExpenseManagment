package dev.reprator.baseUi.imageLoader

import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides

actual interface ImageLoadingPlatformComponent {
    
    @Provides
    fun providePlatformContext(): PlatformContext = PlatformContext.INSTANCE
}