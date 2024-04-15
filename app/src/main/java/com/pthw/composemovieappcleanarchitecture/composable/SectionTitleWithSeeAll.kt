package com.pthw.composemovieappcleanarchitecture.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Composable
fun SectionTitleWithSeeAll(
    modifier: Modifier,
    title: String,
    onSeeAll: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTextView(text = title)
        Row(
            modifier = Modifier
                .padding(vertical = Dimens.MARGIN_MEDIUM)
                .clickable {
                    onSeeAll()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "See All",
                color = ColorPrimary,
                fontSize = Dimens.TEXT_REGULAR
            )
            Icon(
                modifier = Modifier.size(Dimens.MARGIN_20),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "",
                tint = ColorPrimary,
            )
        }
    }
}

@Preview
@Composable
private fun SectionTitleWithSeeAllPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            SectionTitleWithSeeAll(modifier = Modifier, title = "Test Title")
        }
    }
}
