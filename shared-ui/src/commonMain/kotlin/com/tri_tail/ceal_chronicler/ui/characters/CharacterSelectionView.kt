package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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
import java.util.*

@Composable
fun DisplayCharacterSelectionView(koin: Koin) {
    val model: CharacterModel = koin.get()
    var selectedCharacterId: Optional<CharacterId> by remember {
        mutableStateOf(
            model.selectedCharacter
        )
    }

    model.onSelectedCharacterUpdate = {
        selectedCharacterId = it
    }

    if (selectedCharacterId.isPresent) {
        val selectedCharacterIdValue = selectedCharacterId.get()
        val character = model.get(selectedCharacterIdValue)
        if (character.isPresent) {
            DisplayCharacterView(character.get())
        } else {
            DisplayMainContentColumnWithError(
                "Could not find character with ID: $selectedCharacterIdValue",
                model
            )
        }
    } else {
        DisplayMainContentColumn(model)
    }
}

@Composable
fun DisplayMainContentColumnWithError(
    errorMessage: String,
    model: CharacterModel
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier.padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Red)
                .padding(15.dp)
        ) {
            Text(
                text = errorMessage,
                style = typography.h2
            )
            DisplayMainContentColumn(model)
        }
    }
}

@Composable
private fun DisplayMainContentColumn(model: CharacterModel) {

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
private fun DisplayAvailableCharacters(model: CharacterModel) {
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
        DisplayCharacterButton(character)
    }
}

@Composable
private fun DisplayCharacterButton(
    character: Character
) {
    Button(
        onClick = { clickCharacterButton(character.id) },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(text = character.nameAsString)
    }
}

private fun clickCharacterButton(characterId: CharacterId) {
    val eventBus = EventBus.getDefault()
    eventBus.post(SelectCharacterEvent(characterId))
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
