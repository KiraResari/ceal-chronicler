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
import java.util.*

@Composable
fun DisplayCharacterSelector(
    model: CharacterSelectorModel = CharacterSelectorModel()
) {
    var repository: MutableState<CharacterRepository> =
        remember { mutableStateOf(CharacterRepository()) }
    var selectedCharacterId: MutableState<Optional<CharacterId>> =
        remember {
            mutableStateOf(
                model.selectedCharacter
            )
        }

    model.updateSelectedCharacter = {
        selectedCharacterId.value = it
    }

    if (selectedCharacterId.value.isPresent) {
        val selectedCharacterIdValue = selectedCharacterId.value.get();
        val character = repository.value.get(selectedCharacterIdValue)
        if (character.isPresent) {
            DisplayCharacterScreen(character.get())
        } else {
            DisplaySelectableCharactersWithError(
                "Could not find character with ID: " + selectedCharacterIdValue,
                repository.value,
                selectedCharacterId.value
            )
        }
    } else {
        DisplaySelectableCharacters(repository.value, selectedCharacterId.value)
    }
}

@Composable
fun DisplaySelectableCharactersWithError(
    errorMessage: String,
    repository: CharacterRepository,
    selectedCharacterId: Optional<CharacterId>
) {
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
                text = errorMessage,
                style = typography.h2
            )
        }
    }
    DisplaySelectableCharacters(repository, selectedCharacterId)
}

@Composable
private fun DisplaySelectableCharacters(
    repository: CharacterRepository,
    selectedCharacterId: Optional<CharacterId>
) {
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
            DebugDisplaySelectedCharacterId(selectedCharacterId)
        }
    }
}

@Composable
fun DebugDisplaySelectedCharacterId(selectedCharacterId: Optional<CharacterId>) {
    if (selectedCharacterId.isPresent) {
        Text("Selected Character Id: " + selectedCharacterId.get())
    } else {
        Text("No character selected")
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
