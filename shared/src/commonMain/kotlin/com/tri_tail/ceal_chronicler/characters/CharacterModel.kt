package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.events.DeselectCharacterEvent
import com.tri_tail.ceal_chronicler.events.SelectCharacterEvent
import com.tri_tail.ceal_chronicler.items.Weapon
import java.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CharacterModel(private val repository: CharacterRepository) {
    var selectedCharacter: Optional<CharacterId> = Optional.empty()

    var onSelectedCharacterUpdate: ((Optional<CharacterId>) -> Unit) = { }

    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }

    @Subscribe
    fun onSelectCharacterEvent(event: SelectCharacterEvent) {
        selectedCharacter = Optional.of(event.characterId)
        onSelectedCharacterUpdate(selectedCharacter)
    }

    @Subscribe
    fun onDeselectCharacterEvent(event: DeselectCharacterEvent) {
        selectedCharacter = Optional.empty()
        onSelectedCharacterUpdate(selectedCharacter)
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