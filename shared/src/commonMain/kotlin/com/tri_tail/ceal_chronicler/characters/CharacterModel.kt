package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.events.*
import com.tri_tail.ceal_chronicler.items.Weapon
import java.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CharacterModel(private val repository: CharacterRepository) {
    var selectedCharacter: Optional<CharacterId> = Optional.empty()

    var onSelectedCharacterUpdate: ((Optional<CharacterId>) -> Unit) = { }
    var onAvailableCharactersUpdate: ((Iterable<Character>) -> Unit) = { }

    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }

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