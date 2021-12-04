package com.devbea.lotuskmm.android.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.devbea.lotuskmm.android.R
import com.devbea.lotuskmm.android.presentation.theme.Grey2
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

val RECIPE_IMAGE_HEIGHT = 220.dp

@Composable
fun RecipeImage(
    url: String, modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(RECIPE_IMAGE_HEIGHT)
) {
    GlideImage(
        imageModel = url,
        modifier = modifier,
        requestOptions = {
            RequestOptions()
                .override(256, 256)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
        },
        contentScale = ContentScale.Crop,
        shimmerParams = ShimmerParams(
            baseColor = Grey2,
            highlightColor = MaterialTheme.colors.surface,
            durationMillis = 750,
            dropOff = 0.65f,
            tilt = 20f
        ),
        previewPlaceholder = R.drawable.ic_placehoder,
        failure = {
            Text(text = "image request failed.")
        }
    )
}