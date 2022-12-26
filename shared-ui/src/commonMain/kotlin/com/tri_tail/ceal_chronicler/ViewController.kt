package com.tri_tail.ceal_chronicler

import androidx.compose.runtime.Composable
import com.tri_tail.ceal_chronicler.events.OpenCharacterSelectionViewEvent
import com.tri_tail.ceal_chronicler.ui.main_view.MainView
import com.tri_tail.ceal_chronicler.ui.main_view.MainViewState
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ViewController {

    private var mainViewState = MainViewState.TITLE

    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }

    @Composable
    fun draw() {
        MainView(mainViewState)
    }

    @Subscribe
    fun onOpenCharacterSelectionViewEvent(event: OpenCharacterSelectionViewEvent) {
        mainViewState = MainViewState.CHARACTER
    }
}