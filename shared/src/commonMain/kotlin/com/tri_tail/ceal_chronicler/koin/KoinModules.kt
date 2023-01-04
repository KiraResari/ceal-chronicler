package com.tri_tail.ceal_chronicler.koin

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tri_tail.ceal_chronicler.characters.*
import com.tri_tail.ceal_chronicler.main_view.*

object KoinModules {
    val models = module {
        singleOf(::CharacterModel)
        singleOf(::MainViewModel)
    }
    val repositories = module {
        singleOf(::CharacterRepository)
    }
}