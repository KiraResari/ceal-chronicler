package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.KoinTestBase
import org.koin.test.inject
import kotlin.test.*

class CharacterSelectionModelTest : KoinTestBase() {
    private val model: CharacterSelectionModel by inject()

    @BeforeTest
    override fun setup() {
        super.setup()
    }

    @Test
    fun addCharacterShouldIncreaseCharacterCountByOne() {
        val characterCountBeforeAdding = model.getCharacters().count()

        model.addCharacter(Character())

        val characterCountAfterAdding = model.getCharacters().count()
        assertEquals(
            characterCountBeforeAdding + 1,
            characterCountAfterAdding
        )
    }

    @AfterTest
    override fun tearDown() {
        super.tearDown()
    }
}