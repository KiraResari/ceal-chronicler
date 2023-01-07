package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.events.*
import org.greenrobot.eventbus.Subscribe

class CharacterModel(
    var viewData: CharacterViewData,
    private val repository: CharacterRepository
) : BaseModel() {

    var originalCharacter = viewData.getDeepCopyOfCharacter()
    val characterEqualsOriginal
        get() = viewData.character == originalCharacter

    var onViewDataChanged: ((CharacterViewData) -> Unit) = {}

    @Subscribe
    fun onResetCharacterInputEvent(event: ResetCharacterInputEvent) {
        resetCharacter()
    }

    @Subscribe
    fun onSaveCharacterInputEvent(event: SaveCharacterInputEvent) {
        saveCharacter()
    }

    fun resetCharacter() {
        viewData = CharacterViewData(originalCharacter.deepCopy())
        checkIfCharacterEqualsOriginal()
        onViewDataChanged(viewData)
    }

    fun saveCharacter() {
        repository.saveCharacter(viewData.character)
        originalCharacter = viewData.getDeepCopyOfCharacter()
        checkIfCharacterEqualsOriginal()
        onViewDataChanged(viewData)
    }

    fun setCharacterName(nameAsString: String) {
        viewData.setCharacterName(nameAsString)
        checkIfCharacterEqualsOriginal()
        onViewDataChanged(viewData)
    }

    fun setCharacterSpecies(speciesAsString: String) {
        viewData.setCharacterSpecies(speciesAsString)
        checkIfCharacterEqualsOriginal()
        onViewDataChanged(viewData)
    }

    fun setCharacterWeapon(weaponAsString: String) {
        viewData.setCharacterWeapon(weaponAsString)
        checkIfCharacterEqualsOriginal()
        onViewDataChanged(viewData)
    }

    private fun checkIfCharacterEqualsOriginal() {
        viewData.characterEqualsOriginal =
            (viewData.character == originalCharacter)
    }

}