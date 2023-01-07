package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.items.Weapon


class TestCharacter {

    companion object {
        const val DEFAULT_CHARACTER_NAME = "Test Character"
        const val DEFAULT_SPECIES_NAME = "Test Species"
        const val DEFAULT_WEAPON_NAME = "Test Weapon"

        fun build(): Character {
            return Character(
                name = CharacterName(DEFAULT_CHARACTER_NAME),
                species = Species(DEFAULT_SPECIES_NAME),
                weapon = Weapon(DEFAULT_WEAPON_NAME)
            )
        }
    }
}