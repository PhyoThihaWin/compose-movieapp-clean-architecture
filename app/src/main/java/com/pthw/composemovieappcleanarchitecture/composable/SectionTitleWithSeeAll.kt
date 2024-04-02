package com.pthw.composemovieappcleanarchitecture.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Composable
fun SectionTitleWithSeeAll(
    modifier: Modifier,
    title: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTextView(text = title)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See All",
                color = PrimaryColor,
                fontSize = Dimens.TEXT_REGULAR
            )
            Icon(
                modifier = Modifier.size(Dimens.MARGIN_20),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "",
                tint = PrimaryColor,
            )
        }
    }
}
