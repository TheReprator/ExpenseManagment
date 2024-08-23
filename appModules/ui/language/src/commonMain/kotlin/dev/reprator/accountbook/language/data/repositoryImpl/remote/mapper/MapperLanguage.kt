package dev.reprator.accountbook.language.data.repositoryImpl.remote.mapper

import dev.reprator.core.util.Mapper
import dev.reprator.accountbook.language.data.repositoryImpl.remote.EntityLanguage
import dev.reprator.accountbook.language.modals.ModalStateLanguage
import me.tatarka.inject.annotations.Inject

@Inject
class MapperLanguage : Mapper<EntityLanguage, ModalStateLanguage> {

    override fun map(from: EntityLanguage): ModalStateLanguage {
        return ModalStateLanguage(from.name, from.id)
    }

}
