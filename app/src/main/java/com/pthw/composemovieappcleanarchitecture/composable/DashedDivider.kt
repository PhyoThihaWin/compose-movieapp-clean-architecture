package com.pthw.composemovieappcleanarchitecture.composable

/**
 * Created by P.T.H.W on 20/04/2024.
 */

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun DashedDividerPreview() {
    DashedDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}

@Composable
fun DashedDivider(
    modifier: Modifier = Modifier,
    dashWidth: Dp = 4.dp,
    dashHeight: Dp = 2.dp,
    gapWidth: Dp = 2.dp,
    color: Color = Color.LightGray,
) {
    Canvas(modifier) {

        val pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx()),
            phase = 0f
        )

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = dashHeight.toPx()
        )
    }
}
