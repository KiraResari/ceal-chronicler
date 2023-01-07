package com.tri_tail.ceal_chronicler.events

import com.tri_tail.ceal_chronicler.characters.Character

data class OpenCharacterViewEvent(val character: Character): Event() {
}