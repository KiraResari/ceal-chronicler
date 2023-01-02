package com.tri_tail.ceal_chronicler.ui.main_view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import com.tri_tail.ceal_chronicler.main_view.MainViewModel
import com.tri_tail.ceal_chronicler.main_view.MainViewState
import com.tri_tail.ceal_chronicler.theme.AppTheme
import com.tri_tail.ceal_chronicler.ui.TitleScreen
import com.tri_tail.ceal_chronicler.ui.characters.DisplayCharacterSelector

@Composable
fun DisplayMainView(model: MainViewModel = MainViewModel()) {

    var state by remember {
        mutableStateOf(
            model.state,
            policy = neverEqualPolicy()
        )
    }

    model.onStateUpdate = {
        state = it
    }

    AppTheme {
        when (state) {
            MainViewState.TITLE -> TitleScreen()
            MainViewState.CHARACTER -> DisplayCharacterSelector()
        }
    }
}