package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.Character
import com.tri_tail.ceal_chronicler.events.DeselectCharacterEvent
import com.tri_tail.ceal_chronicler.theme.*
import org.greenrobot.eventbus.EventBus

@Composable
fun DisplayCharacterView(character: Character) {
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
                text = character.nameAsString,
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
