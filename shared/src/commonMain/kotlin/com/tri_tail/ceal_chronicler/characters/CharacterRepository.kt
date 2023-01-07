package com.tri_tail.ceal_chronicler.characters

import java.util.*

class CharacterRepository {

    private val characters = HashMap<CharacterId, Character>();

    fun getCharacters(): List<Character> {
        return ArrayList(characters.values);
    }

    fun addCharacter(character: Character) {
        characters[character.id] = character;
    }

    fun saveCharacter(character: Character) {
        val characterId = character.id
        characters[characterId] = character
    }

    fun getCharacter(characterId: CharacterId): Optional<Character> {
        val character = characters[characterId]
        if(character == null){
            return Optional.empty()
        }
        return Optional.of(character.deepCopy())
    }
}