package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.*
import com.tri_tail.ceal_chronicler.events.*
import com.tri_tail.ceal_chronicler.theme.*
import com.tri_tail.ceal_chronicler.utils.ValueWithOriginal
import org.greenrobot.eventbus.EventBus
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

@Composable
fun DisplayCharacterView(character: Character, koin: Koin) {
    val model = koin.get<CharacterModel> { parametersOf(character) }
    var nameDisplayValue
            by remember { mutableStateOf(character.nameAsString) }
    var speciesDisplayValue
            by remember { mutableStateOf(character.speciesAsString) }
    var weaponDisplayValue
            by remember { mutableStateOf(character.weaponAsString) }

    model.onResetCharacterInput = {
        nameDisplayValue = it.nameAsString
        speciesDisplayValue = it.speciesAsString
        weaponDisplayValue = it.weaponAsString
    }

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
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                label = { Text(text = "Name:") },
                value = nameDisplayValue,
                onValueChange = { nameDisplayValue = it },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                label = { Text(text = "Species:") },
                value = speciesDisplayValue,
                onValueChange = { speciesDisplayValue = it },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                label = { Text(text = "Weapon:") },
                value = weaponDisplayValue,
                onValueChange = { weaponDisplayValue = it },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                DisplayBackButton()
                Spacer(modifier = Modifier.width(5.dp))
                DisplayDiscardButton()
            }
        }
    }
}

@Composable
private fun DisplayBackButton() {
    Button(
        onClick = { clickBackButton() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(text = "↩ Back")
    }
}

private fun clickBackButton() {
    val eventBus = EventBus.getDefault()
    eventBus.post(OpenCharacterSelectionViewEvent())
}

@Composable
private fun DisplayDiscardButton() {
    Button(
        onClick = { restoreOriginalValues() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(text = "✖ Discard Changes")
    }
}

fun restoreOriginalValues() {
    val eventBus = EventBus.getDefault()
    eventBus.post(ResetCharacterInputEvent())
}
