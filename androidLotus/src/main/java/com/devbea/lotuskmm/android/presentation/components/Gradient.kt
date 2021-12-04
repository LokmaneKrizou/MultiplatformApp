package com.devbea.lotuskmm.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Gradient(
    size: Dp = 0.dp,
    colors: List<Color> = listOf(
        Color(0x00FFFFFF),
        Color(0x00FFFFFF),
        Color(0x45000000),
        Color(0xB2000000),
    )
) {
    val fillSize = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = colors
            )
        )
    Box(
        modifier = if (size.value == 0f) fillSize else
            Modifier
                .fillMaxWidth()
                .height(size)
                .background(
                    brush = Brush.verticalGradient(
                        colors = colors
                    )
                )
    ) {
    }
}