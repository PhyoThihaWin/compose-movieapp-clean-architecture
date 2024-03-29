package com.pthw.composemovieappcleanarchitecture.feature.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by P.T.H.W on 25/03/2024.
 */

@Composable
fun TicketNavPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxSize(),
    )
}