package com.devbea.lotuskmm.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

private val LightThemeColors = lightColors(
    primary = White,
    primaryVariant = Pink600Dark,
    onPrimary = Black2,
    secondary = Pink600,
    secondaryVariant = Pink100,
    onSecondary = White,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Black1,
    surface = White,
    onSurface = Black2,
)

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AppTheme(
    displayProgressBar: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey1)
        ) {
            content()
            if (displayProgressBar) {
                // TODO("Show indeterminate progress bar")
            }
        }
    }
}