package com.tri_tail.ceal_chronicler

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toPainter
import dev.icerock.moko.resources.ImageResource

@Composable
actual fun painterResource(imageResource: ImageResource): Painter {
    return imageResource.image.toPainter()
}
