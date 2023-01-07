package com.tri_tail.ceal_chronicler.items

import kotlinx.serialization.Serializable

@Serializable
data class Weapon(
    var name: String
): Item(name)
