package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.KoinTestBase
import org.koin.test.inject
import kotlin.test.*

class CharacterRepositoryTest : KoinTestBase() {
    private val repository: CharacterRepository by inject()
    private lateinit var testUtilities: CharacterRepositoryTestUtilities

    @BeforeTest
    override fun setup() {
        super.setup()
        testUtilities = CharacterRepositoryTestUtilities(repository)
    }

    @Test
    fun getCharactersShouldReturnNoCharactersIfRepositoryIsEmpty() {
        val characters = repository.getCharacters()

        assertEquals(0, characters.size)
    }

    @Test
    fun saveCharacterShouldIncreaseCharacterCountByOne() {
        val character: Character = TestCharacter.build()

        repository.saveCharacter(character)

        val characters = repository.getCharacters()
        assertEquals(1, characters.size)
    }

    @Test
    fun getCharacterShouldReturnCharacterEqualToAddedCharacter() {
        val character: Character = TestCharacter.build()
        repository.saveCharacter(character)

        val characterOption = repository.getCharacter(character.id)

        if (characterOption.isPresent()) {
            assertEquals(character, characterOption.get())
        } else {
            fail("getCharacter did not return previously added character")
        }
    }

    @Test
    fun editingCharacterGottenFromRepositoryShouldNotChangeCharacterInRepository() {
        val originalCharacter: Character = TestCharacter.build()
        repository.saveCharacter(originalCharacter)

        val retrievedCharacter = testUtilities.getCharacterFromRepository(originalCharacter.id)
        retrievedCharacter.nameAsString = "Changed Name"

        assertEquals(TestCharacter.TEST_CHARACTER_NAME, originalCharacter.nameAsString)
    }

    @Test
    fun editingCharacterPushedToRepositoryShouldNotChangeCharacterInRepository() {
        val originalCharacter: Character = TestCharacter.build()
        repository.saveCharacter(originalCharacter)

        originalCharacter.nameAsString = "Changed Name"
        val retrievedCharacter = testUtilities.getCharacterFromRepository(originalCharacter.id)

        assertEquals(TestCharacter.TEST_CHARACTER_NAME, retrievedCharacter.nameAsString)
    }


    @AfterTest
    override fun tearDown() {
        super.tearDown()
    }
}