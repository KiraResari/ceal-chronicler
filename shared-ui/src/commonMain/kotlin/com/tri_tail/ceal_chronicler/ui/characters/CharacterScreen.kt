package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.Character
import com.tri_tail.ceal_chronicler.events.DeselectCharacterEvent
import com.tri_tail.ceal_chronicler.theme.primaryColor
import com.tri_tail.ceal_chronicler.theme.primaryDarkColor
import com.tri_tail.ceal_chronicler.theme.typography
import org.greenrobot.eventbus.EventBus
import java.util.*

@Composable
fun DisplayCharacterScreen(character: Character) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
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
                text = character.name,
                style = typography.h1
            )
            Text(
                text = "Species: " + character.species,
                style = typography.body1,
                textAlign = TextAlign.Start
            )
            Text(
                text = "Weapon: " + character.weapon,
                style = typography.body1,
                textAlign = TextAlign.Start
            )
            Button(
                onClick = { clickBackButton() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(text = "↩ Back")
            }
        }
    }
}

private fun clickBackButton() {
    val eventBus = EventBus.getDefault()
    eventBus.post(DeselectCharacterEvent())
}
