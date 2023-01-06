package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.BaseModel
import com.tri_tail.ceal_chronicler.events.*
import org.greenrobot.eventbus.Subscribe

class CharacterModel(var character: Character): BaseModel() {

    var onResetCharacterInput: ((Character) -> Unit) = { }

    @Subscribe
    fun onResetCharacterInputEvent(event: ResetCharacterInputEvent){
        onResetCharacterInput(character)
    }

}