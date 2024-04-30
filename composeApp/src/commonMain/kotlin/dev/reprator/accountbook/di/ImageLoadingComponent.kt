package dev.reprator.accountbook.di

import co.touchlab.kermit.Logger
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.intercept.Interceptor
import dev.reprator.accountbook.utility.base.AppInitializer
import dev.reprator.accountbook.utility.base.ApplicationInfo
import dev.reprator.accountbook.utility.image.ImageLoaderCleanupInitializer
import dev.reprator.accountbook.utility.image.newImageLoader
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

expect interface ImageLoadingPlatformComponent

interface ImageLoadingComponent : ImageLoadingPlatformComponent {
  val imageLoader: ImageLoader

  @Provides
  fun provideImageLoader(
      context: PlatformContext,
      interceptors: Set<Interceptor> = emptySet(),
      info: ApplicationInfo,
      logger: Logger,
  ): ImageLoader = newImageLoader(
    context = context,
    interceptors = interceptors,
    logger = logger,
    debug = info.debugBuild,
    applicationInfo = info,
  )

  @Provides
  @IntoSet
  fun bindImageLoaderCleanupInitializer(initializer: ImageLoaderCleanupInitializer): AppInitializer = initializer
}
