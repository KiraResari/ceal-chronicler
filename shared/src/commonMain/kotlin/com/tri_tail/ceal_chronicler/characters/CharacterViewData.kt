package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.main_view.ViewData

data class CharacterViewData(val character: Character) : ViewData() {

    fun setCharacterName(nameAsString: String) {
        character.nameAsString = nameAsString
    }

    fun setCharacterSpecies(speciesAsString: String) {
        character.speciesAsString = speciesAsString
    }

    fun setCharacterWeapon(weaponAsString: String) {
        character.weaponAsString = weaponAsString
    }

    fun getDeepCopyOfCharacter(): Character {
        return character.deepCopy()
    }
}