package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.*
import com.tri_tail.ceal_chronicler.events.*
import com.tri_tail.ceal_chronicler.theme.*
import org.greenrobot.eventbus.EventBus
import org.koin.core.Koin

@Composable
fun DisplayCharacterSelectionView(koin: Koin) {
    val model: CharacterSelectionModel = koin.get()
    DisplayMainContentColumn(model)
}

@Composable
private fun DisplayMainContentColumn(model: CharacterSelectionModel) {

    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(15.dp)
    ) {
        Column(
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
                text = "Select a character:",
                style = typography.h2
            )
            DisplayAvailableCharacters(model)
            DisplayAddCharacterButton()
        }
    }
}

@Composable
private fun DisplayAvailableCharacters(model: CharacterSelectionModel) {
    var availableCharacters: Iterable<Character> by remember {
        mutableStateOf(
            model.getCharacters(),
            policy = neverEqualPolicy()
        )
    }

    model.onAvailableCharactersUpdate = {
        availableCharacters = it
    }

    for (character in availableCharacters) {
        DisplayCharacterButton(character, model)
    }
}

@Composable
private fun DisplayCharacterButton(
    character: Character,
    model: CharacterSelectionModel
) {
    Button(
        onClick = { clickCharacterButton(character.id, model) },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(text = character.nameAsString)
    }
}

private fun clickCharacterButton(
    characterId: CharacterId,
    model: CharacterSelectionModel
) {
    val characterOption = model.getCharacter(characterId)
    if(characterOption.isPresent()) {
        val character = characterOption.get()
        val eventBus = EventBus.getDefault()
        eventBus.post(OpenCharacterViewEvent(character))
    }
}

@Composable
fun DisplayAddCharacterButton() {
    Button(
        onClick = {
            clickAddCharacterButton()
        }
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add Character",
        )
    }
}

private fun clickAddCharacterButton() {
    val eventBus = EventBus.getDefault()
    eventBus.post(AddCharacterEvent(Character()))
}
