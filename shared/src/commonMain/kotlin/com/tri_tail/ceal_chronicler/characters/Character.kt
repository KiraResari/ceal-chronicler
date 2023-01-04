package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.items.Weapon


data class Character(
    var id: CharacterId = CharacterId(),
    var name: CharacterName = CharacterName("Name not set"),
    var species: Species = Species("Species not set"),
    var weapon: Weapon = Weapon("Weapon not set")
) {
    val nameAsString = name.name
    val speciesAsString = species.name
    val weaponAsString = weapon.name
}
