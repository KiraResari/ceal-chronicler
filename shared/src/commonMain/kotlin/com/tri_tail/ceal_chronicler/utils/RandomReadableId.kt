package com.tri_tail.ceal_chronicler.utils

import java.util.UUID

open class RandomReadableId {
    val id: UUID = UUID.randomUUID()
    private val adjectives = listOf(
        "Alert",
        "Bold",
        "Calm",
        "Dull",
        "Easy",
        "Funny",
        "Great",
        "Hot",
        "Ill",
        "Jolly",
        "Kinky",
        "Lazy",
        "Mad",
        "Nice",
        "Old",
        "Pale",
        "Quaint",
        "Red",
        "Sad",
        "Tame",
        "Ugly",
        "Vain",
        "Weak",
        "Xany",
        "Yummy",
        "Zesty"
    )
    private val adjective = adjectives.random()
    private val animals = listOf(
        "Ape",
        "Bear",
        "Cat",
        "Dog",
        "Eel",
        "Fox",
        "Goat",
        "Horse",
        "Ibex",
        "Jay",
        "Koala",
        "Lynx",
        "Mouse",
        "Newt",
        "Owl",
        "Pig",
        "Quail",
        "Rat",
        "Snake",
        "Tiger",
        "Unau",
        "Vole",
        "Whale",
        "Xerus",
        "Yak",
        "Zebra"
    )
    private val animal = animals.random()
    private val letters = listOf(
        "Alpha",
        "Beta",
        "Gamma",
        "Delta",
        "Epsilon",
        "Zeta",
        "Eta",
        "Theta",
        "Iota",
        "Kappa",
        "Lambda",
        "Mu",
        "Nu",
        "Xi",
        "Omicron",
        "Pi",
        "Rho",
        "Sigma",
        "Tau",
        "Upsilon",
        "Phi",
        "Chi",
        "Psi",
        "Omega",
    )
    private val letter = letters.random()
    private val readableId = adjective + " " + animal + "" + letter

    override fun toString(): String{
        return readableId
    }
}