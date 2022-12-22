package com.tri_tail.ceal_chronicler

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform