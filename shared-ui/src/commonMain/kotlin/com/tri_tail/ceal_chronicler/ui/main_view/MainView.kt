package com.tri_tail.ceal_chronicler.ui.main_view

import androidx.compose.runtime.Composable
import com.tri_tail.ceal_chronicler.theme.AppTheme
import com.tri_tail.ceal_chronicler.ui.TitleScreen
import com.tri_tail.ceal_chronicler.ui.characters.DisplayCharacterSelector

@Composable
fun MainView(mainViewState: MainViewState) {
    AppTheme {
        when(mainViewState){
            MainViewState.TITLE -> TitleScreen()
            MainViewState.CHARACTER -> DisplayCharacterSelector()
        }
    }
}