package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.items.Weapon


class TestCharacter {

    companion object {
        const val TEST_CHARACTER_NAME = "Test Character"
        const val TEST_SPECIES_NAME = "Test Species"
        const val TEST_WEAPON_NAME = "Test Weapon"

        fun build(): Character {
            return Character(
                name = CharacterName(TEST_CHARACTER_NAME),
                species = Species(TEST_SPECIES_NAME),
                weapon = Weapon(TEST_WEAPON_NAME)
            )
        }
    }
}