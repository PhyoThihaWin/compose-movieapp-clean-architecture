package com.pthw.composemovieappcleanarchitecture.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens

/**
 * Created by P.T.H.W on 29/03/2024.
 */
@Composable
fun TitleTextView(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = Dimens.TEXT_XLARGE,
        fontWeight = FontWeight.SemiBold,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}