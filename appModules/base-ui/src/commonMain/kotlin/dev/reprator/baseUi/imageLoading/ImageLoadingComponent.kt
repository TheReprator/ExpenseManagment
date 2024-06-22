package dev.reprator.baseUi.imageLoading

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.intercept.Interceptor
import dev.reprator.appFeatures.api.logger.Logger
import dev.reprator.core.app.ApplicationInfo
import me.tatarka.inject.annotations.Provides

expect interface ImageLoadingPlatformComponent

interface ImageLoadingComponent : ImageLoadingPlatformComponent {

    val imageLoader: ImageLoader

    @Provides
    fun provideImageInterceptor() = emptySet<Interceptor>()

    @Provides
    fun provideImageLoader(
        context: PlatformContext,
        interceptors: Set<Interceptor>,
        info: ApplicationInfo,
        logger: Logger,
    ): ImageLoader = newImageLoader(
        context = context,
        interceptors = interceptors,
        logger = logger,
        debug = info.debugBuild,
        applicationInfo = info,
    )
  }
