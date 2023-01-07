package com.tri_tail.ceal_chronicler.koin

import org.koin.dsl.module
import com.tri_tail.ceal_chronicler.characters.*
import com.tri_tail.ceal_chronicler.main_view.*
import org.koin.core.module.dsl.*

object KoinModules {
    val models = module {
        singleOf(::CharacterSelectionModel)
        singleOf(::MainViewModel)
        factoryOf(::CharacterModel)
    }
    val repositories = module {
        singleOf(::CharacterRepository)
    }
}