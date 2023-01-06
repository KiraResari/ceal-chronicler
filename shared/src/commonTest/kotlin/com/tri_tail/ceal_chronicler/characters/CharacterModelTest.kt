package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.koin.KoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterModelTest : KoinTest {
    private val model: CharacterSelectionModel by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(KoinModules.models, KoinModules.repositories)
            printLogger()
        }
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
    fun tearDown() {
        stopKoin()
    }
}