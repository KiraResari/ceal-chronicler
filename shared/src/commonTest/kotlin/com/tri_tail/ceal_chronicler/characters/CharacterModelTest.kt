package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.KoinTestBase
import org.koin.test.inject
import kotlin.test.*

class CharacterModelTest : KoinTestBase() {
    private val repository: CharacterRepository by inject()
    private lateinit var repositoryTestUtilities: CharacterRepositoryTestUtilities;
    private lateinit var testUtilities: CharacterModelTestUtilities

    @BeforeTest
    override fun setup() {
        super.setup()
        repositoryTestUtilities = CharacterRepositoryTestUtilities(repository)
        testUtilities = CharacterModelTestUtilities(repository)
    }

    @Test
    fun setCharacterNameShouldNotChangeCharacterNameInRepository() {
        val characterId = repositoryTestUtilities.addCharacter(TestCharacter.build())
        val oldCharacterName = repositoryTestUtilities.getCharacterName(characterId)
        val model = testUtilities.prepareModelForCharacter(characterId)

        model.setCharacterName("Changed Character Name")

        var newCharacterName = repositoryTestUtilities.getCharacterName(characterId)
        assertEquals(oldCharacterName, newCharacterName)
    }

    @Test
    fun savingChangedCharacterNameShouldChangeItInRepository(){
        val characterId = repositoryTestUtilities.addCharacter(TestCharacter.build())
        val model = testUtilities.prepareModelForCharacter(characterId)
        val changedCharacterName = "Changed Character Name"

        model.setCharacterName(changedCharacterName)
        model.saveCharacter()

        var repositoryCharacterName = repositoryTestUtilities.getCharacterName(characterId)
        assertEquals(changedCharacterName, repositoryCharacterName)
    }

    @Test
    fun resetCharacterShouldRestoreOriginalCharacterValues(){
        val characterId = repositoryTestUtilities.addCharacter(TestCharacter.build())
        val model = testUtilities.prepareModelForCharacter(characterId)

        model.setCharacterName("Changed Character Name")
        model.resetCharacter()

        var character = model.viewData.character
        assertEquals(TestCharacter.DEFAULT_CHARACTER_NAME, character.nameAsString)
    }

    @AfterTest
    override fun tearDown() {
        super.tearDown()
    }
}