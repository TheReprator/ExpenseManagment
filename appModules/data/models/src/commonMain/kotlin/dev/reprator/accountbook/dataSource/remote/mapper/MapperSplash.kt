package dev.reprator.accountbook.dataSource.remote.mapper

import dev.reprator.accountbook.core.util.Mapper
import dev.reprator.accountbook.modals.networkModal.EntitySplash
import dev.reprator.accountbook.modals.uiModal.ModalLanguage
import dev.reprator.accountbook.modals.uiModal.ModalSplashState
import me.tatarka.inject.annotations.Inject

@Inject
class MapperSplash : Mapper<EntitySplash, ModalSplashState> {

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
