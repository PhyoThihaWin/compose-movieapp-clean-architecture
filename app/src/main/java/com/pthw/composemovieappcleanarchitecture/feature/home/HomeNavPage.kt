package com.pthw.composemovieappcleanarchitecture.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import kotlin.math.absoluteValue

/**
 * Created by P.T.H.W on 27/03/2024.
 */
@Composable
fun HomeNavPage(modifier: Modifier = Modifier) {
    HomePageContent(modifier = modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageContent(modifier: Modifier) {
    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .background(color = Color.Black)
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2, vertical = Dimens.MARGIN_MEDIUM)
            ) {
                Column(modifier.weight(1f)) {
                    Text("Hi, Angelina \uD83D\uDC4B", color = Color.White)
                    Text(
                        "Welcome back",
                        color = Color.White,
                        fontSize = Dimens.TEXT_HEADING,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = ""
                )
            }
        },
    ) { innerPadding ->
        val pagerState = rememberPagerState(pageCount = {
            10
        })

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .background(color = Color.Black)
        ) {
            item {

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM_2))

                HomeSearchBarView(modifier = modifier)

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM_2))

                TitleTextView(
                    modifier = modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                    text = "Now playing"
                )

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                HorizontalPager(
                    modifier = modifier
                        .heightIn(max = (LocalConfiguration.current.screenHeightDp / 3).dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = Dimens.MARGIN_XXXXLARGE,
                        vertical = 8.dp
                    ),
                    state = pagerState
                ) { page ->
                    // Our page content
                    Card(
                        modifier = modifier
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        )

                                alpha = lerp(
                                    start = 0.7f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                                )

                                cameraDistance = 8 * density
                                rotationY = lerp(
                                    start = 0f,
                                    stop = 0f,
                                    fraction = pageOffset.coerceIn(-1f, 1f),
                                )

                                lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                            },

                        content = {
                            AsyncImage(
                                model = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/7a8fa5da-d816-43a7-8e4b-5eb6aafbb825/dgr8qdd-b7743841-157a-4889-ad5e-46a74ef50796.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzdhOGZhNWRhLWQ4MTYtNDNhNy04ZTRiLTVlYjZhYWZiYjgyNVwvZGdyOHFkZC1iNzc0Mzg0MS0xNTdhLTQ4ODktYWQ1ZS00NmE3NGVmNTA3OTYuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.l1FQgFXccI8y_LnnCZPUxRppZZ87U_FqQhJtx0u1lvI",
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                        }

                    )


                }

                HorizontalPagerIndicator(
                    pageCount = 10,
                    currentPage = pagerState.currentPage,
                    targetPage = pagerState.targetPage,
                    currentPageOffsetFraction = pagerState.currentPageOffsetFraction
                )

                Box(
                    modifier = modifier
                        .padding(innerPadding)
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 40.dp),
                )

                Box(
                    modifier = modifier
                        .padding(innerPadding)
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 40.dp),
                )
                Box(
                    modifier = modifier
                        .padding(innerPadding)
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 40.dp),
                )
                Box(
                    modifier = modifier
                        .padding(innerPadding)
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 40.dp),
                )
            }
        }
    }
}

@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = PrimaryColor,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorCornerRadius: Dp = 2.dp,
    indicatorPadding: Dp = 2.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    Color.DarkGray to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
            )
        }
    }
}

@Composable
private fun HomeSearchBarView(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
            .clip(Shapes.small)
            .background(color = Color.DarkGray)
            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search_normal),
            contentDescription = ""
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray),
            value = "", onValueChange = {},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent
            ),
            placeholder = { Text(text = "Search", color = Color.Gray) }
        )
    }

}

@Preview
@Composable
private fun HomeNavPagePreview() {
    ComposeMovieAppCleanArchitectureTheme {
        HomePageContent(modifier = Modifier.background(color = Color.Black))
    }
}