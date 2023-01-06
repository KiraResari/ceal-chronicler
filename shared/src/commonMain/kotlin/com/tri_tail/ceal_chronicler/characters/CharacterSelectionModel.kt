package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.events.*
import java.util.*
import org.greenrobot.eventbus.Subscribe

class CharacterSelectionModel(private val repository: CharacterRepository): BaseModel() {

    var onAvailableCharactersUpdate: ((Iterable<Character>) -> Unit) = { }

    @Subscribe
    fun onAddCharacterEvent(event: AddCharacterEvent){
        repository.add(event.character)
        onAvailableCharactersUpdate(repository.getCharacters())
    }

    fun get(characterId: CharacterId): Optional<Character> {
        return repository.get(characterId)
    }

    fun getCharacters(): Iterable<Character> {
        return repository.getCharacters()
    }

    fun addCharacter(character: Character) {
        repository.add(character)
    }
}