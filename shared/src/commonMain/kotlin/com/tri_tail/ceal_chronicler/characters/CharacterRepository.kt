package com.tri_tail.ceal_chronicler.characters

import java.util.*

class CharacterRepository {

    private val characters = HashMap<CharacterId, Character>();

    fun getCharacters(): List<Character> {
        return ArrayList(characters.values);
    }

    fun saveCharacter(character: Character) {
        characters[character.id] = character.deepCopy();
    }

    fun getCharacter(characterId: CharacterId): Optional<Character> {
        val character = characters[characterId]
        if(character == null){
            return Optional.empty()
        }
        return Optional.of(character.deepCopy())
    }
}