package com.pthw.composemovieappcleanarchitecture.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens

@Composable
fun MovieFavoriteIcon(
    modifier: Modifier,
    isFavorite: Boolean,
    onFavorite: () -> Unit,
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onFavorite()
            }
            .padding(Dimens.MARGIN_MEDIUM),
        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
        tint = if (isFavorite) ColorPrimary else Color.Gray,
        contentDescription = ""
    )
}