package dev.reprator.baseUi.imageLoader

import coil3.ImageLoader
import coil3.PlatformContext
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.core.app.ApplicationInfo
import me.tatarka.inject.annotations.Provides

expect interface ImageLoadingPlatformSpecificComponent

expect interface ImageLoadingPlatformComponent

interface ImageLoadingComponent : ImageLoadingPlatformComponent, ImageLoadingPlatformSpecificComponent {

    val imageLoader: ImageLoader

    @Provides
    fun provideImageLoader(
        context: PlatformContext,
        //interceptors: Set<Interceptor>,
        info: ApplicationInfo,
        logger: Logger,
    ): ImageLoader = newImageLoader(
        context = context,
       // interceptors = interceptors,
        logger = logger,
        debug = info.debugBuild,
        applicationInfo = info,
    )
}
