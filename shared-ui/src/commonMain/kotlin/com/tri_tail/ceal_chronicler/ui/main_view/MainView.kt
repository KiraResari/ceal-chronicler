package com.tri_tail.ceal_chronicler.ui.main_view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import com.tri_tail.ceal_chronicler.characters.*
import org.koin.core.Koin
import com.tri_tail.ceal_chronicler.main_view.MainViewModel
import com.tri_tail.ceal_chronicler.theme.AppTheme
import com.tri_tail.ceal_chronicler.title_screen.TitleScreenViewData
import com.tri_tail.ceal_chronicler.ui.characters.*
import com.tri_tail.ceal_chronicler.ui.title_screen.DisplayTitleScreen

@Composable
fun DisplayMainView(koin: Koin) {

    val model: MainViewModel = koin.get()

    var viewData by remember {
        mutableStateOf(
            model.viewData,
            policy = neverEqualPolicy()
        )
    }

    model.onStateUpdate = {
        viewData = it
    }

    AppTheme {
        when (val localViewData = viewData) {
            is TitleScreenViewData -> DisplayTitleScreen()
            is CharacterSelectionViewData -> DisplayCharacterSelectionView(koin)
            is CharacterViewData -> DisplayCharacterView(localViewData.character)
            else -> throw NotImplementedError("MainView does not know how to handle state: $localViewData")
        }
    }
}