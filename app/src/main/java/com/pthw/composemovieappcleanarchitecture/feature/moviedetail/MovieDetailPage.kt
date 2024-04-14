package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.StarRatingBar
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColorsPalette
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes

/**
 * Created by P.T.H.W on 08/04/2024.
 */

@Composable
fun MovieDetailPage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    PageContent(modifier = modifier) {
        navController.popBackStack()
    }
}

@Composable
private fun PageContent(
    modifier: Modifier,
    onBack: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val isScrolledOnTop by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset == 0 }
    }
    val bgImageHeight = LocalConfiguration.current.screenHeightDp / 3.6
    val bgImageHeightInDp = animateDpAsState(
        targetValue = if (isScrolledOnTop) bgImageHeight.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 200,
        ), label = "CoilAsyncImage"
    )
    val topMarginInDp = animateDpAsState(
        targetValue = if (isScrolledOnTop) (bgImageHeight / 1.4).dp else 0.dp,
        animationSpec = tween(
            durationMillis = 200,
        ), label = "CoilAsyncImage"
    )

    Box(
        modifier = modifier
    ) {
        CoilAsyncImage(
            imageUrl = "https://mlpnk72yciwc.i.optimole.com/cqhiHLc.IIZS~2ef73/w:auto/h:auto/q:75/https://bleedingcool.com/wp-content/uploads/2024/01/godzilla_x_kong_the_new_empire_ver5_xxlg.jpg",
            modifier = modifier.height(bgImageHeightInDp.value)
        )

        AnimatedVisibility(visible = isScrolledOnTop) {
            Icon(
                painterResource(id = R.drawable.ic_arrow_left),
                tint = Color.Black,
                modifier = Modifier
                    .clickable {
                        onBack()
                    }
                    .padding(start = Dimens.MARGIN_MEDIUM_2, top = Dimens.MARGIN_MEDIUM_2)
                    .background(color = Color.Black.copy(0.1f), shape = Shapes.small)
                    .padding(
                        vertical = Dimens.MARGIN_MEDIUM,
                        horizontal = Dimens.MARGIN_MEDIUM
                    )
                    .size(Dimens.MARGIN_LARGE),
                contentDescription = null
            )
        }

        LazyColumn(
            modifier = modifier.padding(
                top = topMarginInDp.value,
                start = Dimens.MARGIN_MEDIUM_2,
                end = Dimens.MARGIN_MEDIUM_2,
            ),
            state = listState
        ) {

            item {

                MovieDetailInfoCardSection()
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM_2))


                MovieInfoDescriptionTexts(
                    title = "Movie genre:",
                    text = "Action, adventure, sci-fi Action, adventure, sci-fi"
                )
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                MovieInfoDescriptionTexts(
                    title = "Censorship:",
                    text = "13+"
                )
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                MovieInfoDescriptionTexts(
                    title = "Language:",
                    text = "English"
                )
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))

                TitleTextView(text = "Storyline")
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                Text(
                    text = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos.... See more",
                    fontSize = Dimens.TEXT_REGULAR,
                )

                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))

                TitleTextView(text = "Director")
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                LazyRow {
                    items(5) {
                        MovieDetailActorListItem()
                    }
                }

                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))

                TitleTextView(text = "Actor")
                Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                LazyRow {
                    items(5) {
                        MovieDetailActorListItem()
                    }
                }

            }

        }
    }
}

@Composable
fun MovieDetailActorListItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = Dimens.MARGIN_MEDIUM)
            .clip(Shapes.medium)
            .background(color = LocalCustomColorsPalette.current.cardBackgroundColor)
            .padding(
                vertical = Dimens.MARGIN_MEDIUM,
                horizontal = Dimens.MARGIN_MEDIUM_2
            )
            .width((LocalConfiguration.current.screenWidthDp / 3).dp)
    ) {
        CoilAsyncImage(
            imageUrl = "https://i.guim.co.uk/img/media/566d3c1ea3a6240ff30937547bc47f9b4f78ca35/43_64_5131_6413/master/5131.jpg?width=465&dpr=1&s=none",
            modifier = Modifier
                .clip(CircleShape)
                .size(Dimens.MARGIN_XXXLARGE)
        )

        Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
        Text(text = "Anthony Russo", fontSize = Dimens.TEXT_REGULAR)
    }
}


@Composable
private fun MovieInfoDescriptionTexts(
    title: String,
    text: String
) {
    Row {
        Text(
            text = title,
            fontSize = Dimens.TEXT_REGULAR,
            modifier = Modifier.weight(1f),
            color = Color.LightGray
        )
        Text(
            text = text,
            modifier = Modifier.weight(2f),
            fontSize = Dimens.TEXT_REGULAR,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Composable
private fun MovieDetailInfoCardSection() {
    Column(
        modifier = Modifier
            .clip(Shapes.medium)
            .fillMaxWidth()
            .background(color = LocalCustomColorsPalette.current.cardBackgroundColor)
            .padding(Dimens.MARGIN_MEDIUM_2)
    ) {
        TitleTextView(text = "Avengers: Infinity War")
        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_SMALL))
        Text(
            text = "2h29m • 16.12.2022",
            color = Color.Gray,
            fontSize = Dimens.TEXT_REGULAR
        )
        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_LARGE))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Review")
            Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_SMALL))
            Icon(
                imageVector = Icons.Rounded.Star,
                tint = PrimaryColor,
                modifier = Modifier.size(Dimens.MARGIN_18),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "4.8",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = Dimens.MARGIN_XSMALL)
                )
                Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_SMALL))
                Text(
                    text = "(1.222)",
                    color = Color.Gray,
                    fontSize = Dimens.TEXT_XSMALL,
                )
            }
        }

        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_SMALL))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var rating by remember { mutableStateOf(1f) } // default rating will be 1
            StarRatingBar(
                maxStars = 5,
                rating = rating,
                starSize = Dimens.MARGIN_XLARGE,
                starSpacing = Dimens.MARGIN_XSMALL,
                onRatingChanged = {
                    rating = it
                }
            )

            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        top = Dimens.MARGIN_MEDIUM,
                        bottom = Dimens.MARGIN_MEDIUM,
                        start = Dimens.MARGIN_SMALL,
                        end = Dimens.MARGIN_6
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    tint = Color.Gray,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(start = Dimens.MARGIN_SMALL))
                Text(
                    text = "Watch trailer",
                    fontSize = Dimens.TEXT_SMALL,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        }


    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            PageContent(modifier = Modifier)
        }
    }
}