package com.tri_tail.ceal_chronicler

import com.tri_tail.ceal_chronicler.koin.KoinModules
import org.koin.core.context.*
import org.koin.test.KoinTest

abstract class KoinTestBase : KoinTest {
    open fun setup() {
        startKoin {
            modules(KoinModules.models, KoinModules.repositories)
            printLogger()
        }
    }

    open fun tearDown() {
        stopKoin()
    }
}