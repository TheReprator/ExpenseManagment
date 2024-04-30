package dev.reprator.accountbook.cleanArchitecture.dataSource.remote.mapper

import dev.reprator.accountbook.cleanArchitecture.dataSource.remote.modal.EntitySplash
import dev.reprator.accountbook.screen.splash.ModalLanguage
import dev.reprator.accountbook.screen.splash.ModalSplashState
import dev.reprator.accountbook.utility.base.Mapper
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
