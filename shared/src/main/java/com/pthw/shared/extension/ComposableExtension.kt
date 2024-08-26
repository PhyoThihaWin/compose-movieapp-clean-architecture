package com.pthw.shared.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

/**
 * Created by P.T.H.W on 24/04/2024.
 */
private object ComposableExtension {
    val shimmerTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 600,
                delayMillis = 400,
                easing = LinearEasing,
            ),
        )
    )
}

fun Modifier.simpleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


@Composable
fun Modifier.showShimmer(shape: Shape = MaterialTheme.shapes.small): Modifier {
    return this
        .shimmer(rememberShimmer(ShimmerBounds.View, ComposableExtension.shimmerTheme))
        .clip(shape)
        .background(Color.LightGray)
        .alpha(0f)
}

@Composable
fun Modifier.addShimmer(state: Boolean): Modifier {
    return if (state) {
        this
            .shimmer()
            .background(Color.LightGray)
            .alpha(0f)
    } else this
}

