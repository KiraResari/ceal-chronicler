package com.tri_tail.ceal_chronicler

class DesktopPlatform : Platform {
    override val name: String = "Desktop"

}

actual fun getPlatform(): Platform = DesktopPlatform()