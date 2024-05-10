package dev.reprator.accountbook.inject

import dev.reprator.core.app.Flavor
import me.tatarka.inject.annotations.Provides

interface ProdApplicationComponent {
    @Provides
    fun provideFlavor(): Flavor = Flavor.Standard
}
