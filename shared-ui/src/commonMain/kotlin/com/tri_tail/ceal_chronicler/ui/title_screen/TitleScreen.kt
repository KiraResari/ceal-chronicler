package com.tri_tail.ceal_chronicler.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.MR
import com.tri_tail.ceal_chronicler.painterResource
import com.tri_tail.ceal_chronicler.theme.primaryColor
import com.tri_tail.ceal_chronicler.theme.primaryDarkColor
import com.tri_tail.ceal_chronicler.theme.typography
import com.tri_tail.ceal_chronicler.ui.main_view.MainViewState
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun TitleScreen(mainViewState: MutableState<MainViewState>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(MR.images.CealChroniclerLogo),
            contentDescription = null
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
                    text = stringResource(MR.strings.test_string),
                    style = typography.h1
                )
            }
        }
        Button(
            onClick = { mainViewState.value = MainViewState.CHARACTER }
        ) {
            Text(text = "Go to Character Screen")
        }
    }
}