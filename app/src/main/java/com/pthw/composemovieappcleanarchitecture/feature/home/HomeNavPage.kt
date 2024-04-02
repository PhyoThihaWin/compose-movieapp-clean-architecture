package com.pthw.composemovieappcleanarchitecture.feature.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.appbase.viewstate.RenderCompose
import com.pthw.appbase.viewstate.renderState
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.SectionTitleWithSeeAll
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.model.MovieVo
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import kotlin.math.absoluteValue

/**
 * Created by P.T.H.W on 27/03/2024.
 */
@SuppressLint("ResourceAsColor")
@Composable
fun HomeNavPage(
    modifier: Modifier = Modifier,
    viewModel: HomeNavPageViewModel = hiltViewModel(),
) {
    val uiState = UiState(
        nowPlayingMovies = viewModel.nowPlayingMovies.value,
        comingSoonMovies = viewModel.upComingMovies.value
    )
    HomePageContent(modifier = modifier, uiState = uiState)
}

private data class UiState(
    val nowPlayingMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val comingSoonMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePageContent(modifier: Modifier, uiState: UiState) {
    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2, vertical = Dimens.MARGIN_MEDIUM)
            ) {
                Column(modifier.weight(1f)) {
                    Text("Hi, Angelina \uD83D\uDC4B")
                    Text(
                        text = "Welcome back",
                        fontSize = Dimens.TEXT_HEADING,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = ""
                )
            }
        },
    ) { innerPadding ->
        val nowPlayingPagerState = rememberPagerState(pageCount = {
            8
        })
        val promoPagerState = rememberPagerState(pageCount = {
            20
        })

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
        ) {
            item {

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM_2))

                HomeSearchBarView(modifier = modifier)

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM_2))

                // Now Playing
                SectionTitleWithSeeAll(
                    modifier = modifier.padding(
                        horizontal = Dimens.MARGIN_MEDIUM_2,
                        vertical = Dimens.MARGIN_MEDIUM_2
                    ),
                    title = "Now playing"
                )

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                RenderCompose(state = uiState.nowPlayingMovies,
                    loading = {
                        CircularProgressIndicator()
                    },
                    success = {
                        NowPlayingMoviesSectionView(
                            modifier = modifier,
                            pagerState = nowPlayingPagerState,
                            movies = it
                        )
                    })


                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                // Coming Soon
                SectionTitleWithSeeAll(
                    modifier = modifier.padding(
                        horizontal = Dimens.MARGIN_MEDIUM_2,
                        vertical = Dimens.MARGIN_MEDIUM_2
                    ),
                    title = "Coming soon"
                )

                RenderCompose(state = uiState.comingSoonMovies,
                    loading = {
                        CircularProgressIndicator()
                    },
                    success = {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                        ) {
                            items(it.size) { index ->
                                ComingSoonMoviesItemView(modifier, it[index])
                            }
                        }
                    }
                )

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))


                // Promo & Discount
                SectionTitleWithSeeAll(
                    modifier = modifier.padding(
                        horizontal = Dimens.MARGIN_MEDIUM_2,
                        vertical = Dimens.MARGIN_MEDIUM_2
                    ),
                    title = "Promo & Discount"
                )

                HorizontalPager(
                    state = promoPagerState,
                    modifier = modifier
                        .heightIn(max = (LocalConfiguration.current.screenHeightDp / 4).dp),
                    pageSpacing = Dimens.MARGIN_MEDIUM_2,
                    contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                ) {
                    AsyncImage(
                        modifier = modifier.clip(Shapes.small),
                        model = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/7a8fa5da-d816-43a7-8e4b-5eb6aafbb825/dgr8qdd-b7743841-157a-4889-ad5e-46a74ef50796.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzdhOGZhNWRhLWQ4MTYtNDNhNy04ZTRiLTVlYjZhYWZiYjgyNVwvZGdyOHFkZC1iNzc0Mzg0MS0xNTdhLTQ4ODktYWQ1ZS00NmE3NGVmNTA3OTYuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.l1FQgFXccI8y_LnnCZPUxRppZZ87U_FqQhJtx0u1lvI",
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                // Service
                SectionTitleWithSeeAll(
                    modifier = modifier.padding(
                        horizontal = Dimens.MARGIN_MEDIUM_2,
                        vertical = Dimens.MARGIN_MEDIUM_2
                    ),
                    title = "Celebrities"
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2),
                ) {
                    items(20) {
                        CelebritiesItemView(modifier = modifier)
                    }
                }

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                // Movie News
                SectionTitleWithSeeAll(
                    modifier = modifier.padding(
                        horizontal = Dimens.MARGIN_MEDIUM_2,
                        vertical = Dimens.MARGIN_MEDIUM_2
                    ),
                    title = "Movie News"
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2),
                ) {
                    items(20) {
                        Column(
                            modifier = modifier
                                .widthIn(max = (LocalConfiguration.current.screenHeightDp / 3).dp)
                                .padding(end = Dimens.MARGIN_MEDIUM_2)
                        ) {
                            AsyncImage(
                                modifier = modifier
                                    .heightIn(max = (LocalConfiguration.current.screenHeightDp / 5).dp)
                                    .clip(Shapes.small),
                                model = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/7a8fa5da-d816-43a7-8e4b-5eb6aafbb825/dgr8qdd-b7743841-157a-4889-ad5e-46a74ef50796.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzdhOGZhNWRhLWQ4MTYtNDNhNy04ZTRiLTVlYjZhYWZiYjgyNVwvZGdyOHFkZC1iNzc0Mzg0MS0xNTdhLTQ4ODktYWQ1ZS00NmE3NGVmNTA3OTYuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.l1FQgFXccI8y_LnnCZPUxRppZZ87U_FqQhJtx0u1lvI",
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                            Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
                            Text(
                                text = "When The Batman 2 Starts Filming Reportedly Revealed",
                                fontSize = Dimens.TEXT_REGULAR_2,
                            )
                        }
                    }
                }

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_LARGE))

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NowPlayingMoviesSectionView(
    modifier: Modifier,
    pagerState: PagerState,
    movies: List<MovieVo>
) {
    Column {
        HorizontalPager(
            modifier = modifier
                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2.8).dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 80.dp,
                vertical = 8.dp
            ),
            state = pagerState
        ) { page ->
            HorizontalPagerItemView(
                modifier = modifier,
                pagerState = pagerState,
                currentPage = page,
                movieVo = movies[page]
            )
        }

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        TitleTextView(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.MARGIN_XXXLARGE),
            text = movies[pagerState.currentPage].title,
            maxLines = 1
        )

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_SMALL))

        Text(
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "2h29m • Action, adventure, sci-fi",
            fontSize = Dimens.TEXT_REGULAR_2
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Star, "", tint = PrimaryColor)
                TitleTextView(modifier = modifier, text = "4.8")
            }

            Text(
                "(1.222)",
                fontSize = Dimens.TEXT_XSMALL,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        HorizontalPagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.targetPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )
    }

}

@Composable
private fun CelebritiesItemView(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(end = Dimens.MARGIN_MEDIUM_2)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape),
            model = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/7a8fa5da-d816-43a7-8e4b-5eb6aafbb825/dgr8qdd-b7743841-157a-4889-ad5e-46a74ef50796.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzdhOGZhNWRhLWQ4MTYtNDNhNy04ZTRiLTVlYjZhYWZiYjgyNVwvZGdyOHFkZC1iNzc0Mzg0MS0xNTdhLTQ4ODktYWQ1ZS00NmE3NGVmNTA3OTYuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.l1FQgFXccI8y_LnnCZPUxRppZZ87U_FqQhJtx0u1lvI",
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
        Text(text = "Keanau Reeves", fontSize = Dimens.TEXT_REGULAR_2, textAlign = TextAlign.Center)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerItemView(
    modifier: Modifier,
    pagerState: PagerState,
    currentPage: Int,
    movieVo: MovieVo
) {
    val pageOffset = pagerState.currentPage - currentPage + pagerState.currentPageOffsetFraction
    Card(
        modifier = modifier
            .graphicsLayer {
                alpha = lerp(
                    start = 0.5f,
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
                    fraction = 1f - pageOffset.absoluteValue + 0.2f,
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },

        content = {
            AsyncImage(
                modifier = modifier.fillMaxSize(),
                model = movieVo.posterPath,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }
    )
}

@Composable
private fun ComingSoonMoviesItemView(
    modifier: Modifier,
    movieVo: MovieVo
) {
    Column(
        modifier = modifier
            .width(180.dp)
            .padding(end = Dimens.MARGIN_MEDIUM_2)
    ) {
        AsyncImage(
            model = movieVo.posterPath,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = modifier
                .height(220.dp)
                .width(180.dp)
                .clip(Shapes.small)
        )

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        Text(
            text = movieVo.title,
            fontSize = Dimens.TEXT_REGULAR_2,
            fontWeight = FontWeight.Medium,
            color = PrimaryColor,
            minLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.ic_video_info), "")
            Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
            Text(text = "Adventure, Sci-fi", fontSize = Dimens.TEXT_SMALL)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.ic_calendar), "")
            Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
            Text(text = "20.1.2024", fontSize = Dimens.TEXT_SMALL)
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
    selectedIndicatorSize: Dp = 12.dp,
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
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray),
            value = "", onValueChange = {},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Search", color = Color.Gray) }
        )
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeNavPageNightPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        HomePageContent(modifier = Modifier, uiState = UiState())
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeNavPagePreview() {
    ComposeMovieAppCleanArchitectureTheme {
        HomePageContent(modifier = Modifier, uiState = UiState())
    }
}

@Preview
@Composable
private fun SectionTitleWithSeeAllPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            SectionTitleWithSeeAll(modifier = Modifier, title = "Promo & Test")
        }
    }
}

@Preview
@Composable
private fun CelebritiesItemViewPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            CelebritiesItemView(modifier = Modifier)
        }
    }
}