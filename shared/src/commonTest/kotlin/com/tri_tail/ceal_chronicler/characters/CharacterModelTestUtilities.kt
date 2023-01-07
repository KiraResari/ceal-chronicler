package com.tri_tail.ceal_chronicler.characters

import kotlin.test.fail

class CharacterModelTestUtilities(private val repository: CharacterRepository) {
    fun prepareModelForCharacter(characterId: CharacterId): CharacterModel {
        val characterOption = repository.getCharacter(characterId)
        if (characterOption.isPresent()) {
            val character = characterOption.get()
            val viewData = CharacterViewData(character)
            return CharacterModel(viewData, repository)
        }
        fail("Character with id '$characterId' was not found in repository")
    }

}