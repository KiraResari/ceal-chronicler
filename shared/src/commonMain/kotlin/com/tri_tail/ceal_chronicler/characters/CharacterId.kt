package com.tri_tail.ceal_chronicler.characters

import com.tri_tail.ceal_chronicler.utils.RandomReadableId
import kotlinx.serialization.Serializable

@Serializable
data class CharacterId(val id: RandomReadableId = RandomReadableId())