package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.events.*
import org.greenrobot.eventbus.Subscribe

class CharacterModel(
    var viewData: CharacterViewData,
    private val repository: CharacterRepository
) : BaseModel() {

    var originalCharacter = viewData.getDeepCopyOfCharacter()

    var onViewDataChanged: ((Character) -> Unit) = {}

    @Subscribe
    fun onResetCharacterInputEvent(event: ResetCharacterInputEvent) {
        resetCharacter()
    }

    @Subscribe
    fun onSaveCharacterInputEvent(event: SaveCharacterInputEvent) {
        saveCharacter()
    }

    fun resetCharacter() {
        viewData = CharacterViewData(originalCharacter)
        onViewDataChanged(viewData.character)
    }

    fun saveCharacter() {
        repository.saveCharacter(viewData.character)
        originalCharacter = viewData.getDeepCopyOfCharacter()
    }

    fun setCharacterName(nameAsString: String) {
        viewData.setCharacterName(nameAsString)
    }

    fun setCharacterSpecies(speciesAsString: String) {
        viewData.setCharacterSpecies(speciesAsString)
    }

    fun setCharacterWeapon(weaponAsString: String) {
        viewData.setCharacterWeapon(weaponAsString)
    }

}