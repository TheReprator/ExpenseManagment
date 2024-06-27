package dev.reprator.accountbook.language.data.repositoryImpl.remote.mapper

import dev.reprator.accountbook.core.util.Mapper
import dev.reprator.accountbook.language.data.repositoryImpl.remote.EntityLanguage
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import me.tatarka.inject.annotations.Inject

@Inject
class MapperLanguage : Mapper<List<EntityLanguage>, List<ModalStateLanguage>> {

    override fun map(from: List<EntityLanguage>): List<ModalStateLanguage> {
        
        return from.map {
            ModalStateLanguage(it.name, it.id)
        }
    }

}
