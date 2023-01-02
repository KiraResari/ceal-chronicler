package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.events.DeselectCharacterEvent
import com.tri_tail.ceal_chronicler.events.SelectCharacterEvent
import java.util.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CharacterSelectorModel {
    var selectedCharacter: Optional<CharacterId> = Optional.empty()

    var updateSelectedCharacter: ((Optional<CharacterId>) -> Unit) = { }

    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }

    @Subscribe
    fun onSelectCharacterEvent(event: SelectCharacterEvent){
        selectedCharacter = Optional.of(event.characterId)
        updateSelectedCharacter(selectedCharacter)
    }

    @Subscribe
    fun onDeselectCharacterEvent(event: DeselectCharacterEvent){
        selectedCharacter = Optional.empty()
        updateSelectedCharacter(selectedCharacter)
    }


}