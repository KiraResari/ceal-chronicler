package com.tri_tail.ceal_chronicler.main_view

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.characters.*
import com.tri_tail.ceal_chronicler.events.*
import com.tri_tail.ceal_chronicler.title_screen.TitleScreenViewData
import org.greenrobot.eventbus.Subscribe

class MainViewModel: BaseModel() {

    var viewData: ViewData = TitleScreenViewData()

    var onStateUpdate: ((ViewData) -> Unit) = { }

    @Subscribe
    fun onOpenCharacterSelectionViewEvent(event: OpenCharacterSelectionViewEvent) {
        viewData = CharacterSelectionViewData()
        onStateUpdate(viewData)
    }

    @Subscribe
    fun onSelectCharacterEvent(event: SelectCharacterEvent) {
        viewData = CharacterViewData(event.character)
        onStateUpdate(viewData)
    }
}