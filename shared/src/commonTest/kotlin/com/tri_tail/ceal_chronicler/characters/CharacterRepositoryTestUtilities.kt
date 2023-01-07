package com.tri_tail.ceal_chronicler.characters

import kotlin.test.fail

class CharacterRepositoryTestUtilities(private val repository: CharacterRepository) {
    fun getCharacterName(characterId: CharacterId): String {
        val character = getCharacterFromRepository(characterId)
        return character.nameAsString
    }

    fun addCharacter(character: Character): CharacterId{
        repository.saveCharacter(character)
        return character.id
    }

    fun getCharacterFromRepository(characterId: CharacterId): Character {
        val characterOption = repository.getCharacter(characterId)
        if (characterOption.isPresent()) {
            return characterOption.get()
        }
        fail("Character with id '$characterId' was not found in repository")
    }
}