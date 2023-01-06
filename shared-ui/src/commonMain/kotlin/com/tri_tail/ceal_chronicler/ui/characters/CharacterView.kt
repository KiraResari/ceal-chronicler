package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.*
import com.tri_tail.ceal_chronicler.events.OpenCharacterSelectionViewEvent
import com.tri_tail.ceal_chronicler.theme.*
import org.greenrobot.eventbus.EventBus

@Composable
fun DisplayCharacterView(character: Character) {
    var nameDisplayValue
            by remember { mutableStateOf(character.nameAsString) }
    var speciesDisplayValue
            by remember { mutableStateOf(character.speciesAsString) }
    var weaponDisplayValue
            by remember { mutableStateOf(character.weaponAsString) }
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
                text = nameDisplayValue,
                style = typography.h1
            )
            TextField(
                label = { Text(text = "Name:") },
                value = nameDisplayValue,
                onValueChange = {
                    character.nameAsString = it
                    nameDisplayValue = it
                },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            TextField(
                label = { Text(text = "Species:") },
                value = speciesDisplayValue,
                onValueChange = {
                    character.speciesAsString = it
                    speciesDisplayValue = it
                },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            TextField(
                label = { Text(text = "Weapon:") },
                value = weaponDisplayValue,
                onValueChange = {
                    character.weaponAsString = it
                    weaponDisplayValue = it
                },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
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
    eventBus.post(OpenCharacterSelectionViewEvent())
}
