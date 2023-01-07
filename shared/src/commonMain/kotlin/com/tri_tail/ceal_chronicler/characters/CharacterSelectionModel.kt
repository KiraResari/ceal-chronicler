package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.events.*
import java.util.*
import org.greenrobot.eventbus.Subscribe

class CharacterSelectionModel(private val repository: CharacterRepository): BaseModel() {

    var onAvailableCharactersUpdate: ((Iterable<Character>) -> Unit) = { }

    @Subscribe
    fun onAddCharacterEvent(event: AddCharacterEvent){
        repository.saveCharacter(event.character)
        onAvailableCharactersUpdate(repository.getCharacters())
    }

    fun getCharacter(characterId: CharacterId): Optional<Character>{
        return repository.getCharacter(characterId)
    }

    fun getCharacters(): Iterable<Character> {
        return repository.getCharacters()
    }

    fun addCharacter(character: Character) {
        repository.saveCharacter(character)
    }
}