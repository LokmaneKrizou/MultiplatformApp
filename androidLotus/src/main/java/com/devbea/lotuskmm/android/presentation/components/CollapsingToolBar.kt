package com.devbea.lotuskmm.android.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CollapsingToolBar(
    appBarContent: @Composable RowScope.() -> Unit,
    screenContent: LazyListScope.() -> Unit
) {
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
            modifier = Modifier.fillMaxWidth(),
            content = screenContent
        )

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
            content = appBarContent
        )
    }
}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
