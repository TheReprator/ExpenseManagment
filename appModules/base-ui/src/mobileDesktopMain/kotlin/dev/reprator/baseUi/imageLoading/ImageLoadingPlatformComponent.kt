package dev.reprator.baseUi.imageLoading

import me.tatarka.inject.annotations.Provides
import okio.FileSystem
import dev.reprator.core.appinitializers.AppInitializer
import me.tatarka.inject.annotations.IntoSet

actual interface ImageLoadingPlatformComponent {


    @Provides
    fun provideFileSystem(): FileSystem = FileSystem.SYSTEM
    
    @Provides
    @IntoSet
    fun bindImageLoaderCleanupInitializer(initializer: ImageLoaderCleanupInitializer): AppInitializer = initializer

}
