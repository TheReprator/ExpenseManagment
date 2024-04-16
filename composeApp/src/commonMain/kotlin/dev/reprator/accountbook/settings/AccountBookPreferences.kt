// Copyright 2019, Google LLC, Christopher Banes and the Tivi project contributors
// SPDX-License-Identifier: Apache-2.0

package dev.reprator.accountbook.settings

import kotlinx.coroutines.flow.Flow

interface AccountBookPreferences {
  suspend fun setTheme(theme: Theme)
  fun observeTheme(): Flow<Theme>

  enum class Theme {
    LIGHT,
    DARK,
    SYSTEM,
  }
}
