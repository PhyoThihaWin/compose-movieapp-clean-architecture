package com.pthw.composemovieappcleanarchitecture.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by P.T.H.W on 27/03/2024.
*/

@Composable
fun ProfileNavPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Green)
            .fillMaxSize(),
    )
}