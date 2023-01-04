package com.tri_tail.ceal_chronicler.main_view

import com.tri_tail.ceal_chronicler.events.*
import com.tri_tail.ceal_chronicler.main_view.state.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainViewModel {

    var state: MainViewState = TitleScreenViewState()

    var onStateUpdate: ((MainViewState) -> Unit) = { }

    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }

    @Subscribe
    fun onOpenCharacterSelectionViewEvent(event: OpenCharacterSelectionViewEvent) {
        state = CharacterSelectionViewState()
        onStateUpdate(state)
    }

    @Subscribe
    fun onSelectCharacterEvent(event: SelectCharacterEvent) {
        state = CharacterViewState(event.character)
        onStateUpdate(state)
    }
}