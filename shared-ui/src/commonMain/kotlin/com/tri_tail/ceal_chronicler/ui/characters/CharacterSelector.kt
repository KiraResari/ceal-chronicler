package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.Character
import com.tri_tail.ceal_chronicler.characters.CharacterId
import com.tri_tail.ceal_chronicler.characters.CharacterRepository
import com.tri_tail.ceal_chronicler.characters.CharacterSelectorModel
import com.tri_tail.ceal_chronicler.events.SelectCharacterEvent
import com.tri_tail.ceal_chronicler.theme.primaryColor
import com.tri_tail.ceal_chronicler.theme.primaryDarkColor
import com.tri_tail.ceal_chronicler.theme.typography
import org.greenrobot.eventbus.EventBus
import org.koin.core.Koin
import java.util.*

@Composable
fun DisplayCharacterSelector(koin: Koin) {
    val model: CharacterSelectorModel = koin.get()
    val repository: CharacterRepository = koin.get()
    val selectedCharacterId: MutableState<Optional<CharacterId>> =
        remember {
            mutableStateOf(
                model.selectedCharacter
            )
        }

    model.onSelectedCharacterUpdate = {
        selectedCharacterId.value = it
    }

    if (selectedCharacterId.value.isPresent) {
        val selectedCharacterIdValue = selectedCharacterId.value.get();
        val character = repository.get(selectedCharacterIdValue)
        if (character.isPresent) {
            DisplayCharacterScreen(character.get())
        } else {
            DisplaySelectableCharactersWithError(
                "Could not find character with ID: " + selectedCharacterIdValue,
                repository
            )
        }
    } else {
        DisplaySelectableCharacters(repository)
    }
}

@Composable
fun DisplaySelectableCharactersWithError(
    errorMessage: String,
    repository: CharacterRepository
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
            DisplaySelectableCharacters(repository)
        }
    }
}

@Composable
private fun DisplaySelectableCharacters(repository: CharacterRepository) {
    val characters: Iterable<Character> =
        repository.getCharacters();

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
            for (character in characters) {
                DisplayCharacterButton(character)
            }
        }
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
        Text(text = character.name)
    }
}

private fun clickCharacterButton(characterId: CharacterId) {
    val eventBus = EventBus.getDefault()
    eventBus.post(SelectCharacterEvent(characterId))
}
