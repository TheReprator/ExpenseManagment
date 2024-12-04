package dev.reprator.country.data.mapper

import dev.reprator.base.mapper.AppMapper
import dev.reprator.country.KOIN_NAMED_MAPPER
import dev.reprator.country.data.TableCountryEntity
import dev.reprator.modals.country.CountryModal
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
@Named(KOIN_NAMED_MAPPER)
class CountryResponseMapper : AppMapper<TableCountryEntity, CountryModal> {

    override suspend fun map(from: TableCountryEntity): CountryModal {
        return CountryModal.DTO(from.id.value, from.name, from.isocode, from.shortcode)
    }

}