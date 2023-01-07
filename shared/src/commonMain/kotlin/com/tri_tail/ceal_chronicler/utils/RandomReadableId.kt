package com.tri_tail.ceal_chronicler.utils

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class RandomReadableId(
    val id: Long = System.currentTimeMillis() + Random.nextLong(9999)
) {
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
    private val adjective = adjectives[id.mod(adjectives.size)]
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
    private val animal = animals[(id / 10).mod(animals.size)]
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
    private val letter = letters[(id / 100).mod(letters.size)]
    private val readableId = "$adjective $animal $letter"

    override fun toString(): String {
        return readableId
    }
}