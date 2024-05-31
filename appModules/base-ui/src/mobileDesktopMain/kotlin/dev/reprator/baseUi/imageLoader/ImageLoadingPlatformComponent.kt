package dev.reprator.baseUi.imageLoader

import me.tatarka.inject.annotations.Provides
import dev.reprator.core.appinitializers.AppInitializer
import me.tatarka.inject.annotations.IntoSet
import okio.FileSystem
import okio.SYSTEM

actual interface ImageLoadingPlatformComponent {
    
    @Provides
    fun provideFileSystem(): FileSystem {
        println("vikramPath111")
        return FileSystem.SYSTEM
    }

    @Provides
    @IntoSet
    fun bindImageLoaderCleanupInitializer(initializer: ImageLoaderCleanupInitializer): AppInitializer =
        initializer
}
