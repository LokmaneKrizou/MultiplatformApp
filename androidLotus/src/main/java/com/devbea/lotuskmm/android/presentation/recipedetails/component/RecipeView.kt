package com.devbea.lotuskmm.android.presentation.recipedetails.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.constraintlayout.compose.ConstraintLayout
import com.devbea.lotuskmm.android.presentation.components.Gradient
import com.devbea.lotuskmm.android.presentation.components.RecipeImage
import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.toRecipe
import com.devbea.lotuskmm.domain.model.Recipe
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun RecipeView(recipe: Recipe) {
    val scrollState = rememberLazyListState()
    val toolbarHeight = 328.dp
    val toolBarMinOffsetHeight = -toolbarHeight * 1.8f
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }

    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val isNearToTop =
                    (scrollState.layoutInfo.totalItemsCount / 4f).roundToInt() >= scrollState.firstVisibleItemIndex
                if (((toolbarOffsetHeightPx.value < toolBarMinOffsetHeight.value) && delta < 0) || scrollState.isScrolledToTheEnd()) return Offset.Zero
                if (!isNearToTop && delta > 0) return Offset.Zero
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            // attach as a parent to the nested scroll system
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(top = toolbarHeight),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = recipe.title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )

                    }
                }
            }
            itemsIndexed(recipe.ingredients) { index, ingredient ->
                Text(
                    text = ingredient,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start)
                        .padding(8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }

        TopAppBar(
            modifier = Modifier
                .height(toolbarHeight)
                .offset {
                    IntOffset(
                        x = 0,
                        y = toolbarOffsetHeightPx.value.roundToInt()
                    )
                },
            contentPadding = PaddingValues(0.dp),
            content = {
                ConstraintLayout {
                    val (image) = createRefs()
                    val (fab) = createRefs()
                    RecipeImage(
                        url = recipe.featuredImage,
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(image)
                            {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            })
                    Gradient()
                    FloatingActionButton(
                        onClick = { }, modifier = Modifier
                            .size(55.dp)
                            .padding(8.dp)
                            .constrainAs(fab)
                            {
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {
                        Text(
                            text = recipe.rating.toString(),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

            }
        )
    }


}


@Composable
private fun CategoryDetailsCollapsingToolbar(
    recipe: Recipe,
    scrollOffset: Float,
    content: @Composable () -> Unit
) {
    val imageSize by animateDpAsState(
        targetValue = max(
            100.dp,
            326.dp * scrollOffset
        )
    )
    Scaffold(

    ) {
        content()
    }

}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview
@Composable
private fun RecipeViewPreview() {
    RecipeView(
        recipe = RecipeDto(
            1,
            "title",
            "me",
            "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
            5,
            "",
            listOf("tomato", "batata"),
            123,
            123
        ).toRecipe()
    )
}