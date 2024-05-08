package dev.reprator.accountbook.splash.dataSource.remote.mapper

import dev.reprator.accountbook.core.util.Mapper
import dev.reprator.accountbook.splash.dataSource.remote.modal.EntitySplash
import dev.reprator.accountbook.splash.ModalLanguage
import dev.reprator.accountbook.splash.ModalSplashState
import me.tatarka.inject.annotations.Inject

@Inject
class MapperSplash constructor(): Mapper<EntitySplash, ModalSplashState> {

    override fun map(from: EntitySplash): ModalSplashState {
        val imageList = from.imageList
        val languageList = from.languageList.map {
            ModalLanguage(it.name, it.id)
        }
        return ModalSplashState(
            languageList, imageList
        )
    }

}
