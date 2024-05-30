package dev.reprator.baseUi.imageLoader

import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides
import okio.FileSystem
import okio.NodeJsFileSystem

actual interface ImageLoadingPlatformComponent {
    
    @Provides
    fun providePlatformContext(): PlatformContext = PlatformContext.INSTANCE

    @Provides
    fun provideFileSystem(): FileSystem {
        return NodeJsFileSystem
    }
}