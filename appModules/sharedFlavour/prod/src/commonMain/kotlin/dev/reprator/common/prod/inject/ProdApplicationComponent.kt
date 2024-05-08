package dev.reprator.common.prod.inject

import dev.reprator.core.app.Flavor
import me.tatarka.inject.annotations.Provides

interface ProdApplicationComponent {
    @Provides
    fun provideFlavor(): Flavor = Flavor.Standard
}
