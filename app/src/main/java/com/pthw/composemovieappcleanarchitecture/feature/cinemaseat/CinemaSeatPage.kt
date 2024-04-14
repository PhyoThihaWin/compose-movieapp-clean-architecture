package com.pthw.composemovieappcleanarchitecture.feature.cinemaseat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens

/**
 * Created by P.T.H.W on 14/04/2024.
 */

@Composable
fun CinemaSeatPage(
    modifier: Modifier = Modifier
) {
    PageContent(modifier = modifier)
}

@Composable
private fun PageContent(
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            Box(
                modifier = modifier
                    .padding(vertical = Dimens.MARGIN_MEDIUM),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    modifier = Modifier
                        .clickable {
                            // todo()
                        }
                        .padding(
                            vertical = Dimens.MARGIN_XSMALL,
                            horizontal = Dimens.MARGIN_MEDIUM
                        ),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                Text(
                    "Movies",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = Dimens.TEXT_REGULAR_3, fontWeight = FontWeight.Medium
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .background(color = Color.Cyan)
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }

}

@Preview
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            PageContent(modifier = Modifier)
        }
    }
}