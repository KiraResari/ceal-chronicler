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
import org.greenrobot.eventbus.EventBus
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

@Composable
fun DisplayCharacterView(viewData: CharacterViewData, koin: Koin) {
    val model = koin.get<CharacterModel> {
        parametersOf(
            viewData
        )
    }
    var nameDisplayString by remember {
        mutableStateOf(model.viewData.character.nameAsString)
    }
    var speciesDisplayString by remember {
        mutableStateOf(model.viewData.character.speciesAsString)
    }
    var weaponDisplayString by remember {
        mutableStateOf(model.viewData.character.weaponAsString)
    }

    model.onViewDataChanged = {
        nameDisplayString = it.nameAsString
        speciesDisplayString = it.speciesAsString
        weaponDisplayString = it.weaponAsString
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
                text = nameDisplayString,
                style = typography.h1
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                label = { Text(text = "Name:") },
                value = nameDisplayString,
                onValueChange = {
                    nameDisplayString = it
                    model.setCharacterName(it)
                },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                label = { Text(text = "Species:") },
                value = speciesDisplayString,
                onValueChange = {
                    speciesDisplayString = it
                    model.setCharacterSpecies(it)
                },
                textStyle = typography.body1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = textFieldBackgroundColor
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                label = { Text(text = "Weapon:") },
                value = weaponDisplayString,
                onValueChange = {
                    weaponDisplayString = it
                    model.setCharacterWeapon(it)
                },
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
                Spacer(modifier = Modifier.width(5.dp))
                DisplaySaveButton()
            }
        }
    }
}

@Composable
private fun DisplayBackButton() {
    Button(
        onClick = { clickBackButton() },
        colors = ButtonDefaults.buttonColors(backgroundColor = cancelButtonColor)
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
        onClick = { clickDiscardButton() },
        colors = ButtonDefaults.buttonColors(backgroundColor = cancelButtonColor)
    ) {
        Text(text = "✖ Discard Changes")
    }
}

fun clickDiscardButton() {
    val eventBus = EventBus.getDefault()
    eventBus.post(ResetCharacterInputEvent())
}

@Composable
private fun DisplaySaveButton() {
    Button(
        onClick = { clickSaveButton() },
        colors = ButtonDefaults.buttonColors(backgroundColor = confirmButtonColor)
    ) {
        Text(text = "✔ Save Changes")
    }
}

fun clickSaveButton() {
    val eventBus = EventBus.getDefault()
    eventBus.post(SaveCharacterInputEvent())
}
