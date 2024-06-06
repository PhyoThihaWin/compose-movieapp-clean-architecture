package com.pthw.composemovieappcleanarchitecture.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable

/**
 * Created by P.T.H.W on 06/06/2024.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun SharedAnimatedContent(
    content: @Composable SharedTransitionScope.(AnimatedContentScope) -> Unit
) {
    SharedTransitionScope {
        AnimatedContent(targetState = true, label = "") {
            this@SharedTransitionScope.content(this)
        }
    }

}