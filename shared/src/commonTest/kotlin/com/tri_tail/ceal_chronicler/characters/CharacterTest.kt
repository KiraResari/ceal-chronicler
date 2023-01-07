package com.tri_tail.ceal_chronicler.characters

import kotlin.test.*

class CharacterTest {

    @Test
    fun deepCopyShouldCreateCharacterEqualToOriginal(){
        val original = TestCharacter.build()

        val deepCopy = original.deepCopy()

        assertEquals(original, deepCopy)
    }
}