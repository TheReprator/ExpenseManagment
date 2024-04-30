package dev.reprator.accountbook.utility.image

import dev.reprator.accountbook.di.ApplicationCoroutineScope
import dev.reprator.accountbook.utility.base.AppCoroutineDispatchers
import dev.reprator.accountbook.utility.base.AppInitializer
import dev.reprator.accountbook.utility.base.ApplicationInfo
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import okio.FileSystem
import okio.Path.Companion.toPath

@Inject
class ImageLoaderCleanupInitializer(
    private val scope: ApplicationCoroutineScope,
    private val dispatchers: AppCoroutineDispatchers,
    private val applicationInfo: ApplicationInfo,
    private val fileSystem: Lazy<FileSystem>,
) : AppInitializer {

  override fun initialize() {
    scope.launch(dispatchers.io) {
      // We delete ImageLoader's disk cache folder to claim back space for the user
      val cachePath = applicationInfo.cachePath().toPath()
      val fs = fileSystem.value

      for (folder in FOLDERS) {
        val path = cachePath.resolve(folder)
        if (fs.exists(path)) {
          fs.deleteRecursively(path)
        }
      }
    }
  }
}

private val FOLDERS = listOf("image_cache", "image_loader_cache")
