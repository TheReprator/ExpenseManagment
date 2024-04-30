package dev.reprator.accountbook.utility.base

data class ApplicationInfo(
  val packageName: String,
  val debugBuild: Boolean,
  val versionName: String,
  val versionCode: Int,
  val cachePath: () -> String,
)
