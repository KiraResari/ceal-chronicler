package com.tri_tail.ceal_chronicler.characters


data class Character(
    var id: CharacterId = CharacterId(),
    var name: String = "Sylvia Zerin",
    var species: String = "Nefilim",
    var weapon: String = "Axe"
)
