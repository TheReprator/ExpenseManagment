package dev.reprator.common.qa.inject

import dev.reprator.core.app.Flavor
import me.tatarka.inject.annotations.Provides

interface QaApplicationComponent {
    @Provides
    fun provideFlavor(): Flavor = Flavor.Qa
}
