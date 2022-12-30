package com.tri_tail.ceal_chronicler.models.main_view

import com.tri_tail.ceal_chronicler.events.OpenCharacterSelectionViewEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainViewModel {

    private var state = MainViewState.TITLE

    var updateState: ((MainViewState) -> Unit) = { }
        set(value) {
            field = value
            updateState(state)
        }

    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }

    @Subscribe
    fun onOpenCharacterSelectionViewEvent(event: OpenCharacterSelectionViewEvent) {
        state = MainViewState.CHARACTER
        updateState(state)
    }


}