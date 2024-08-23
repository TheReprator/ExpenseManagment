package dev.reprator.accountbook.splash.data.repositoryImpl.remote.mapper

import dev.reprator.core.util.Mapper
import dev.reprator.accountbook.splash.data.repositoryImpl.remote.EntitySplash
import dev.reprator.accountbook.splash.modals.ModalStateLanguage
import dev.reprator.accountbook.splash.modals.ModalStateSplash
import me.tatarka.inject.annotations.Inject

@Inject
class MapperSplash : Mapper<EntitySplash, ModalStateSplash> {

    override fun map(from: EntitySplash): ModalStateSplash {
        val imageList = from.imageList
        val languageList = from.languageList.map {
            ModalStateLanguage(it.name, it.id)
        }
        return ModalStateSplash(
            languageList, imageList
        )
    }

}
