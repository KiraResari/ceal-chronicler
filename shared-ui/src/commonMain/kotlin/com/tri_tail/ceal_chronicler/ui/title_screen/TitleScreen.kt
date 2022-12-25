package com.tri_tail.ceal_chronicler.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.theme.primaryColor
import com.tri_tail.ceal_chronicler.theme.primaryDarkColor
import com.tri_tail.ceal_chronicler.theme.typography
import com.tri_tail.ceal_chronicler.ui.main_view.MainViewState

@Composable
fun TitleScreen(mainViewState: MutableState<MainViewState>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = getCealChroniclerLogo(),
            contentDescription = "Ceal Chronicler Logo"
        )
        Card(
            elevation = 10.dp,
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor,
                                primaryDarkColor,
                            )
                        )
                    )
                    .padding(15.dp)
            ) {
                Text(
                    text = "Welcome to the Ceal Chronicler!",
                    style = typography.h1
                )
            }
        }
        Button(
            onClick = { mainViewState.value = MainViewState.CHARACTER }
        ) {
            Text(text = "Go to Character Selection")
        }
    }
}