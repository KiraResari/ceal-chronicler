package com.tri_tail.ceal_chronicler.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.tri_tail.ceal_chronicler.characters.Character
import com.tri_tail.ceal_chronicler.characters.CharacterRepository
import com.tri_tail.ceal_chronicler.theme.primaryColor
import com.tri_tail.ceal_chronicler.theme.primaryDarkColor
import com.tri_tail.ceal_chronicler.theme.typography
import java.util.*

@Composable
fun DisplayCharacterSelector() {
    val selectedCharacter = remember { mutableStateOf(Optional.empty<Character>()) }
    if (selectedCharacter.value.isPresent) {
        DisplayCharacterScreen(selectedCharacter)
    } else {
        DisplaySelectableCharacters(selectedCharacter)
    }
}

@Composable
private fun DisplaySelectableCharacters(
    selectedCharacter: MutableState<Optional<Character>>
) {
    val characterRepository = remember { mutableStateOf(CharacterRepository()) }
    val characters: ArrayList<Character> =
        characterRepository.value.getCharacters();

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
                DisplayCharacterButton(character, selectedCharacter)
            }
        }
    }
}

@Composable
private fun DisplayCharacterButton(
    character: Character,
    selectedCharacter: MutableState<Optional<Character>>
) {
    Button(
        onClick = { selectedCharacter.value = Optional.of(character) },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(text = character.name)
    }
}
