package com.tri_tail.ceal_chronicler

import org.greenrobot.eventbus.EventBus

abstract class BaseModel {
    init {
        val eventBus = EventBus.getDefault()
        eventBus.register(this)
    }
}