package com.tri_tail.ceal_chronicler.events

import com.tri_tail.ceal_chronicler.characters.CharacterId

data class SelectCharacterEvent(val characterId: CharacterId): Event() {
}