package dev.reprator.language.data.mapper

import dev.reprator.base.mapper.AppMapper
import dev.reprator.language.KOIN_NAMED_MAPPER_LANGUAGE
import dev.reprator.language.data.TableLanguage
import dev.reprator.language.modal.LanguageModal
import org.jetbrains.exposed.sql.ResultRow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
@Named(KOIN_NAMED_MAPPER_LANGUAGE)
class LanguageResponseMapper : AppMapper<ResultRow, LanguageModal> {

    override suspend fun map(from: ResultRow): LanguageModal {
        return LanguageModal.DTO(from[TableLanguage.id], from[TableLanguage.name])
    }

}