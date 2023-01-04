package com.tri_tail.ceal_chronicler.events

import com.tri_tail.ceal_chronicler.characters.Character

data class SelectCharacterEvent(val character: Character): Event() {
}