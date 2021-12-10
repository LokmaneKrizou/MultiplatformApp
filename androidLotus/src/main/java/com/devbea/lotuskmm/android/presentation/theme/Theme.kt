package com.devbea.lotuskmm.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.devbea.lotuskmm.android.presentation.components.ProcessDialogQueue
import com.devbea.lotuskmm.android.presentation.recipes.components.CircularProgressBar
import com.devbea.lotuskmm.domain.model.GenericMessageInfo
import com.devbea.lotuskmm.domain.model.UIComponentType
import com.devbea.lotuskmm.domain.util.Queue
import com.devbea.lotuskmm.presentation.recipe_list.ScreenPosition
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    dialogQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
    onRemoveHeadFromQueue: () -> Unit,
    positionProgressBar: Float = ScreenPosition.TOP.value,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = MaterialTheme.colors.isLight
    MaterialTheme(
        colors = LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Pink600Dark,
                darkIcons = false
            )
            systemUiController.setNavigationBarColor(color = Grey1, darkIcons = false)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey1)
        ) {
            when (dialogQueue.items.firstOrNull()?.uiComponentType) {
                UIComponentType.Dialog -> {
                    ProcessDialogQueue(
                        dialogQueue = dialogQueue,
                        onRemoveHeadFromQueue = onRemoveHeadFromQueue
                    )
                    content()
                    CircularProgressBar(
                        isDisplayed = displayProgressBar,
                        verticalBias = positionProgressBar
                    )
                }

                UIComponentType.None -> {

                }
            }
            content()
            CircularProgressBar(
                isDisplayed = displayProgressBar,
                verticalBias = positionProgressBar
            )
        }
    }
}