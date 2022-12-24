package com.tri_tail.ceal_chronicler.characters

class CharacterRepository {

    private val characters = ArrayList<Character>();

    init {
        characters.add(Character())
        characters.add(
            Character("Idra Kegis", "Dragon", "Claws")
        )
    }
    fun getCharacters(): ArrayList<Character> {
        return characters;
    }
}