package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.events.*
import org.greenrobot.eventbus.Subscribe

class CharacterModel(
    var viewData: CharacterViewData,
    private val repository: CharacterRepository
) : BaseModel() {

    var originalCharacter = viewData.getCopyOfCharacter()

    var onViewDataChanged: ((Character) -> Unit) = {}

    @Subscribe
    fun onResetCharacterInputEvent(event: ResetCharacterInputEvent) {
        viewData = CharacterViewData(originalCharacter)
        onViewDataChanged(viewData.character)
    }

    @Subscribe
    fun onSaveCharacterInputEvent(event: SaveCharacterInputEvent) {
        saveCharacter()
    }

    fun saveCharacter() {
        repository.saveCharacter(viewData.character)
        originalCharacter = viewData.getCopyOfCharacter()
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