package dev.reprator.accountbook.splash

import dev.reprator.accountbook.core.util.Mapper
import dev.reprator.accountbook.modals.uiModal.ModalSplashState
import me.tatarka.inject.annotations.Inject

@Inject
class SplashMapperUi : Mapper<ModalSplashState, SplashModalState> {

    override fun map(from: ModalSplashState): SplashModalState {
        val imageList = from.imageList
        val languageList = from.languageList.map {
            SplashModalLanguage(it.language, it.id)
        }
        return SplashModalState(
            languageList, imageList
        )
    }

}
