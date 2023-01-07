package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.items.Weapon
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Character(
    var id: CharacterId = CharacterId(),
    var name: CharacterName = CharacterName("Name not set"),
    var species: Species = Species("Species not set"),
    var weapon: Weapon = Weapon("Weapon not set")
) {
    var nameAsString
        get() = name.name
        set(value) {
            name.name = value
        }
    var speciesAsString
        get() = species.name
        set(value) {
            species.name = value
        }
    var weaponAsString
        get() = weapon.name
        set(value) {
            weapon.name = value
        }

    fun deepCopy(): Character{
        return Json.decodeFromString(Json.encodeToString(this))
    }
}
