package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.items.Weapon
import java.util.*

class CharacterRepository {

    private val characters = HashMap<CharacterId, Character>();

    init {
        add(Character())
        add(
            Character(
                name = CharacterName("Idra Kegis"),
                species = Species("Dragon"),
                weapon = Weapon("Claws")
            )
        )
    }

    fun getCharacters(): Iterable<Character> {
        return characters.values;
    }

    fun get(characterId: CharacterId): Optional<Character> {
        val character = characters.get(characterId)
        if(character == null){
            return Optional.empty()
        }
        return Optional.of(character)
    }

    fun add(character: Character) {
        characters.put(character.id, character);
    }
}