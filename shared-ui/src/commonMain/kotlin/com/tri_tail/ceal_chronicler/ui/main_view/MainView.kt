package com.tri_tail.ceal_chronicler.ui.main_view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tri_tail.ceal_chronicler.theme.AppTheme
import com.tri_tail.ceal_chronicler.ui.TitleScreen
import com.tri_tail.ceal_chronicler.ui.character_screen.CharacterScreen

@Composable
fun MainView() {
    val mainViewState = remember { mutableStateOf(MainViewState.CHARACTER) }
    AppTheme {
        when(mainViewState.value){
            MainViewState.TITLE -> TitleScreen()
            MainViewState.CHARACTER -> CharacterScreen()
        }
    }
}