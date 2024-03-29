package com.pthw.composemovieappcleanarchitecture.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens

/**
 * Created by P.T.H.W on 29/03/2024.
 */
@Composable
fun TitleTextView(
    modifier: Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = Dimens.TEXT_XLARGE,
        color = Color.White,
        fontWeight = FontWeight.SemiBold
    )
}