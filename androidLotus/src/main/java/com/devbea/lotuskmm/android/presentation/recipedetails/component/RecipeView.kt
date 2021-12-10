package com.devbea.lotuskmm.android.presentation.recipedetails.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.devbea.lotuskmm.android.presentation.components.CollapsingToolBar
import com.devbea.lotuskmm.android.presentation.components.Gradient
import com.devbea.lotuskmm.android.presentation.components.RecipeImage
import com.devbea.lotuskmm.datasource.network.model.RecipeDto
import com.devbea.lotuskmm.datasource.network.toRecipe
import com.devbea.lotuskmm.domain.model.Recipe
import com.devbea.lotuskmm.domain.util.DatetimeUtil

@ExperimentalStdlibApi
@ExperimentalMaterialApi
@Composable
fun RecipeView(recipe: Recipe) {

    CollapsingToolBar(
        appBarContent = {
            RecipeToolBar(recipe)

        },
        screenContent = {
            recipeContent(recipe)
        })
}

@ExperimentalStdlibApi
private fun LazyListScope.recipeContent(recipe: Recipe) {
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
            val dateTimeUtil = remember { DatetimeUtil() }
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                text = "Updated at ${dateTimeUtil.humanizeDatetime(recipe.dateUpdated)} by ${recipe.publisher}",
                style = MaterialTheme.typography.caption
            )
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

@Composable
private fun RecipeToolBar(recipe: Recipe) {
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

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalStdlibApi
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