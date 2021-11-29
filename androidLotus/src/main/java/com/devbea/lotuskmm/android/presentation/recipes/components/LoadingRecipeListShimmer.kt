package com.devbea.lotuskmm.android.presentation.recipes.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingRecipeListShimmer(
    imageHeight: Dp,
    padding: Dp = 16.dp
) {

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val cardWidthInPixel = with(LocalDensity.current) { (maxWidth - (padding * 2)).toPx() }
        val cardHeightInPixel = with(LocalDensity.current) { (imageHeight - (padding)).toPx() }
        val gradientWidth = (0.2f * cardHeightInPixel)
        val infiniteTransition = rememberInfiniteTransition()
        val xCardShimmer = infiniteTransition.getCardShimmerFor((cardWidthInPixel + gradientWidth))
        val yCardShimmer = infiniteTransition.getCardShimmerFor((cardHeightInPixel + gradientWidth))
        val colors = listOf(
            Color.LightGray.copy(0.9f),
            Color.LightGray.copy(0.3f),
            Color.LightGray.copy(0.9f)
        )
        LazyColumn {
            items(5) {
                ShimmerRecipeCardItem(
                    colors = colors,
                    xShimmer = xCardShimmer.value,
                    yShimmer = yCardShimmer.value,
                    cardHeight = imageHeight,
                    gradientWidth = gradientWidth,
                    padding = padding
                )
            }
        }
    }
}

@Composable
private fun InfiniteTransition.getCardShimmerFor(targetValue: Float) = this.animateFloat(
    initialValue = 0f,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(
        animation = tween(durationMillis = 1300, easing = LinearEasing, delayMillis = 300),
        repeatMode = RepeatMode.Restart
    )
)
