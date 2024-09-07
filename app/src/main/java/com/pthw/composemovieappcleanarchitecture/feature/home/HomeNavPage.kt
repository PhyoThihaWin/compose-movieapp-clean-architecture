@file:OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class
)

package com.pthw.composemovieappcleanarchitecture.feature.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pthw.appbase.utils.ResultRender
import com.pthw.appbase.utils.ResultState
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.SectionTitleWithSeeAll
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.feature.listing.navigateToMovieListingPage
import com.pthw.composemovieappcleanarchitecture.feature.moviedetail.navigateToMovieDetailPage
import com.pthw.composemovieappcleanarchitecture.feature.search.navigateToSearchMoviesPage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import com.pthw.composemovieappcleanarchitecture.AppConstant
import com.pthw.composemovieappcleanarchitecture.composable.MovieFavoriteIcon
import com.pthw.composemovieappcleanarchitecture.feature.listing.MovieListingPageNavigation
import com.pthw.composemovieappcleanarchitecture.feature.search.HomeSearchBarView
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalColorScheme
import com.pthw.shared.extension.roundTo
import com.pthw.shared.extension.showShimmer
import com.pthw.shared.extension.simpleClickable
import timber.log.Timber
import kotlin.math.absoluteValue

/**
 * Created by P.T.H.W on 27/03/2024.
 */
@Composable
fun HomeNavPage(
    modifier: Modifier = Modifier,
    viewModel: HomeNavPageViewModel = hiltViewModel(),
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val uiState = UiState(
        refreshing = viewModel.refreshing.value,
        nowPlayingMovies = viewModel.nowPlayingMovies.value,
        comingSoonMovies = viewModel.upComingMovies.value,
        popularMovies = viewModel.popularMovies.value,
        popularActors = viewModel.popularPeople.value
    )

    HomePageContent(
        modifier = modifier,
        uiState = uiState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onAction = {
            when (it) {
                UiEvent.Refresh -> viewModel.refreshHomeData()
                UiEvent.NowPlayingSeeAll -> navController.navigateToMovieListingPage(
                    MovieListingPageNavigation.NOW_PLAYING
                )

                UiEvent.ComingSoonSeeAll -> navController.navigateToMovieListingPage(
                    MovieListingPageNavigation.COMING_SOON
                )

                is UiEvent.ItemClick -> {
                    navController.navigateToMovieDetailPage(
                        sharedKey = it.sharedKey,
                        movie = it.movie
                    )
                }

                is UiEvent.FavoriteMovie -> viewModel.favoriteMovie(it.movieId)

                UiEvent.GoSearch -> navController.navigateToSearchMoviesPage()
            }
        },
    )

}

private data class UiState(
    val refreshing: Boolean = false,
    val nowPlayingMovies: ResultState<List<MovieVo>> = ResultState.Idle,
    val comingSoonMovies: ResultState<List<MovieVo>> = ResultState.Idle,
    val popularMovies: ResultState<List<MovieVo>> = ResultState.Idle,
    val popularActors: ResultState<List<ActorVo>> = ResultState.Idle
)

private sealed class UiEvent {
    data object Refresh : UiEvent()
    data object NowPlayingSeeAll : UiEvent()
    data object ComingSoonSeeAll : UiEvent()
    data class ItemClick(val sharedKey: String, val movie: MovieVo) : UiEvent()
    data class FavoriteMovie(val movieId: Int) : UiEvent()
    data object GoSearch : UiEvent()
}

@Composable
private fun HomePageContent(
    modifier: Modifier,
    uiState: UiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onAction: (UiEvent) -> Unit = {},
) {
    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2, vertical = Dimens.MARGIN_MEDIUM)
            ) {
                Column(modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.txt_hi_three),
                        fontSize = Dimens.TEXT_REGULAR
                    )
                    Text(
                        text = stringResource(R.string.txt_welcome_back),
                        fontSize = Dimens.TEXT_XLARGE,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
    ) { innerPadding ->
        val promoPagerState = rememberPagerState { 20 }
        val state = rememberPullToRefreshState()

        Timber.i("Reached: HomePageContent")

        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            state = state,
            isRefreshing = uiState.refreshing,
            onRefresh = {
                onAction(UiEvent.Refresh)
            },
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = Dimens.BTN_COMMON_HEIGHT)
            ) {
                item {
                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    HomeSearchBarView(
                        modifier = Modifier
                            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                            .clickable {
                                onAction(UiEvent.GoSearch)
                            },
                        hint = stringResource(R.string.txt_search),
                        enable = false,
                        onValueChange = {}
                    )

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Now Playing
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = stringResource(R.string.txt_now_playing)
                    ) {
                        onAction(UiEvent.NowPlayingSeeAll)
                    }

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_SMALL))

                    ResultRender(uiState.nowPlayingMovies,
                        loading = {
                            Timber.i("Reached: ResultRender loading")
                            NowPlayingMoviesShimmer(movies = MovieVo.fakeMovies)
                        },
                        success = {
                            Timber.i("Reached: ResultRender success")
                            NowPlayingMoviesSectionView(
                                modifier = modifier,
                                animatedContentScope = animatedContentScope,
                                sharedTransitionScope = sharedTransitionScope,
                                movies = it.take(8),
                                onAction = onAction
                            )
                        })

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Coming Soon
                    SectionTitleWithSeeAll(
                        modifier = modifier.padding(vertical = Dimens.MARGIN_MEDIUM_2),
                        title = stringResource(R.string.txt_coming_soon)
                    ) {
                        onAction(UiEvent.ComingSoonSeeAll)
                    }

                    ResultRender(uiState.comingSoonMovies,
                        loading = {
                            ComingSoonMoviesShimmer()
                        },
                        success = {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                            ) {
                                items(it.size) { index ->
                                    ComingSoonMoviesItemView(
                                        modifier, it[index],
                                        sharedTransitionScope = sharedTransitionScope,
                                        animatedContentScope = animatedContentScope,
                                        onAction = onAction
                                    )
                                }
                            }
                        }
                    )

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))


                    // Promo & Discount
                    TitleTextView(
                        text = stringResource(R.string.txt_promo_discount),
                        modifier = modifier.padding(Dimens.MARGIN_MEDIUM_2),
                    )

                    ResultRender(uiState.popularMovies,
                        loading = {
                            PromotionAndDiscountShimmer()
                        },
                        success = {
                            if (it.isEmpty()) return@ResultRender
                            PromotionAndDiscountSection(
                                promoPagerState, modifier,
                                sharedTransitionScope,
                                animatedContentScope,
                                it,
                                onAction
                            )
                        }
                    )


                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Service
                    TitleTextView(
                        modifier = modifier.padding(Dimens.MARGIN_MEDIUM_2),
                        text = stringResource(R.string.txt_celebrities)
                    )

                    ResultRender(uiState.popularActors,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2),
                            ) {
                                items(it.size) { index ->
                                    CelebritiesItemView(modifier = modifier, actorVo = it[index])
                                }
                            }
                        })

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                    // Movie News
                    TitleTextView(
                        modifier = modifier.padding(Dimens.MARGIN_MEDIUM_2),
                        text = stringResource(R.string.txt_movie_news)
                    )


                    ResultRender(uiState.nowPlayingMovies,
                        loading = {
                            CircularProgressIndicator()
                        },
                        success = {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2),
                            ) {
                                items(it.size) { index ->
                                    MovieNewsSection(modifier, it, index)
                                }
                            }
                        })

                    Spacer(modifier = modifier.padding(top = Dimens.MARGIN_LARGE))

                }
            }
        }
    }
}

@Composable
private fun MovieNewsSection(
    modifier: Modifier,
    it: List<MovieVo>,
    index: Int
) {
    Column(
        modifier = modifier
            .widthIn(max = (LocalConfiguration.current.screenHeightDp / 3).dp)
            .padding(end = Dimens.MARGIN_MEDIUM_2)
    ) {
        CoilAsyncImage(
            modifier = modifier
                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 5).dp)
                .clip(Shapes.small),
            imageUrl = it[index].backdropPath,
        )
        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
        Text(
            text = buildAnnotatedString {
                if (it[index].overview.length > 100) {
                    append(it[index].overview.substring(0..100))
                    append("... ")
                    withStyle(style = SpanStyle(color = ColorPrimary)) {
                        append("See more")
                    }
                } else {
                    append(it[index].overview)
                }
            },
            minLines = 5,
            fontSize = Dimens.TEXT_SMALL,
        )
    }
}

@Composable
private fun PromotionAndDiscountShimmer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.MARGIN_MEDIUM_2)
            .heightIn((LocalConfiguration.current.screenHeightDp / 4).dp)
            .showShimmer()
    )
}

@Composable
private fun PromotionAndDiscountSection(
    promoPagerState: PagerState,
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    movies: List<MovieVo>,
    onAction: (UiEvent) -> Unit,
) {
    HorizontalPager(
        state = promoPagerState,
        modifier = modifier
            .heightIn(max = (LocalConfiguration.current.screenHeightDp / 4).dp),
        pageSpacing = Dimens.MARGIN_MEDIUM_2,
        contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
    ) { index ->
        with(sharedTransitionScope) {
            Box {
                CoilAsyncImage(
                    modifier = modifier
                        .fillMaxSize()
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = AppConstant.PromotionMoviesKey.format(movies[index].id)
                            ),
                            animatedVisibilityScope = animatedContentScope,
                        )
                        .clip(Shapes.small)
                        .simpleClickable {
                            onAction(
                                UiEvent.ItemClick(
                                    AppConstant.PromotionMoviesKey.format(movies[index].id),
                                    movies[index]
                                )
                            )
                        },
                    imageUrl = movies[index].backdropPath,
                )
                MovieFavoriteIcon(
                    modifier = Modifier.align(Alignment.TopEnd),
                    isFavorite = movies[index].isFavorite,
                ) {
                    onAction(UiEvent.FavoriteMovie(movies[index].id))
                }
            }
        }
    }
}

@Composable
private fun NowPlayingMoviesShimmer(
    modifier: Modifier = Modifier,
    movies: List<MovieVo> = MovieVo.fakeMovies,
) {
    val pagerState = rememberPagerState(pageCount = {
        movies.size
    })
    Column {
        HorizontalPager(
            modifier = modifier
                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2.8).dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 90.dp,
                vertical = 8.dp
            ),
            pageSpacing = Dimens.MARGIN_SMALL,
            state = pagerState
        ) { page ->
            val pageOffset = pagerState.currentPage - page + pagerState.currentPageOffsetFraction
            Card(
                modifier = modifier
                    .graphicsLayer {
                        alpha = lerp(
                            start = 0.8f,
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
                    }
                    .showShimmer(),
                content = {
                    Box(modifier = Modifier.fillMaxSize())
                }
            )

        }

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        TitleTextView(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.MARGIN_XXXLARGE)
                .showShimmer(),
            text = movies[pagerState.currentPage].title,
            maxLines = 1
        )

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_SMALL))

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.MARGIN_XLARGE)
                .showShimmer(),
            textAlign = TextAlign.Center,
            text = "${movies[pagerState.currentPage].releaseDate} • ${
                movies[pagerState.currentPage].genreIds.joinToString(
                    ", "
                )
            }",
            fontSize = Dimens.TEXT_REGULAR_2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = modifier.alpha(0f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Star, "", tint = ColorPrimary)
                TitleTextView(
                    modifier = modifier,
                    text = movies[pagerState.currentPage].voteAverage.roundTo(1).toString()
                )
            }

            Text(
                "(1.222)",
                fontSize = Dimens.TEXT_XSMALL,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

        HorizontalPagerIndicator(
            modifier = Modifier
                .width(100.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .showShimmer(),
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.targetPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )
    }
}

@Composable
fun ComingSoonMoviesShimmer(modifier: Modifier = Modifier) {
    val movieVo = MovieVo.fakeMovies.first()
    LazyRow(
        contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
    ) {
        items(4) { index ->
            Column(
                modifier = modifier
                    .width(180.dp)
                    .padding(end = Dimens.MARGIN_MEDIUM_2)

            ) {
                CoilAsyncImage(
                    imageUrl = movieVo.posterPath,
                    modifier = modifier
                        .height(220.dp)
                        .width(180.dp)
                        .showShimmer()
                        .clip(Shapes.small)
                )

                Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

                Text(
                    text = movieVo.title,
                    fontSize = Dimens.TEXT_REGULAR_2,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.showShimmer()
                )

                Row(
                    modifier = Modifier.alpha(0f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_video_info), "")
                    Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                    Text(
                        text = movieVo.genreIds.joinToString(", "), fontSize = Dimens.TEXT_SMALL,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.showShimmer(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_calendar), "")
                    Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                    Text(text = movieVo.releaseDate, fontSize = Dimens.TEXT_SMALL)
                }
            }
        }
    }
}

@Composable
private fun NowPlayingMoviesSectionView(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    movies: List<MovieVo>,
    onAction: (UiEvent) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = {
        movies.size
    })
    Column {
        HorizontalPager(
            modifier = modifier
                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2.8).dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 90.dp,
                vertical = 8.dp
            ),
            pageSpacing = Dimens.MARGIN_SMALL,
            state = pagerState
        ) { page ->
            HorizontalPagerItemView(
                modifier = modifier.clickable {
                    onAction(
                        UiEvent.ItemClick(
                            AppConstant.NowPlayingMoviesKey.format(movies[page].id),
                            movies[page]
                        )
                    )
                },
                pagerState = pagerState,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                currentPage = page,
                movieVo = movies[page],
                onAction = onAction
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
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.MARGIN_XLARGE),
            textAlign = TextAlign.Center,
            text = "${movies[pagerState.currentPage].releaseDate} • ${
                movies[pagerState.currentPage].genreIds.joinToString(
                    ", "
                )
            }",
            fontSize = Dimens.TEXT_REGULAR_2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Star, "", tint = ColorPrimary)
                TitleTextView(
                    modifier = modifier,
                    text = movies[pagerState.currentPage].voteAverage.roundTo(1).toString()
                )
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
private fun CelebritiesItemView(
    modifier: Modifier,
    actorVo: ActorVo?
) {
    Column(
        modifier = modifier
            .padding(end = Dimens.MARGIN_MEDIUM_2)
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilAsyncImage(
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape),
            imageUrl = actorVo?.profilePath.toString(),
        )
        Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
        Text(
            text = actorVo?.name.orEmpty(),
            fontSize = Dimens.TEXT_REGULAR_2,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HorizontalPagerItemView(
    modifier: Modifier,
    pagerState: PagerState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    currentPage: Int,
    movieVo: MovieVo,
    onAction: (UiEvent) -> Unit,
) {
    val pageOffset = pagerState.currentPage - currentPage + pagerState.currentPageOffsetFraction
    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .sharedElement(
                    state = rememberSharedContentState(
                        key = AppConstant.NowPlayingMoviesKey.format(movieVo.id)
                    ),
                    animatedVisibilityScope = animatedContentScope,
                )
                .graphicsLayer {
                    alpha = lerp(
                        start = 0.8f,
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
                Box {
                    CoilAsyncImage(
                        modifier = modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                lerp(
                                    1f,
                                    1.2f,
                                    1.2f - pageOffset.absoluteValue + 0.2f
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                            },
                        imageUrl = movieVo.posterPath,
                    )
                    MovieFavoriteIcon(
                        modifier = Modifier.align(Alignment.TopEnd),
                        isFavorite = movieVo.isFavorite,
                    ) {
                        onAction(UiEvent.FavoriteMovie(movieVo.id))
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ComingSoonMoviesItemView(
    modifier: Modifier,
    movieVo: MovieVo,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onAction: (UiEvent) -> Unit
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier
                .width(180.dp)
                .padding(end = Dimens.MARGIN_MEDIUM_2)
                .simpleClickable {
                    onAction(
                        UiEvent.ItemClick(
                            AppConstant.ComingSoonMoviesKey.format(movieVo.id),
                            movieVo
                        )
                    )
                }
        ) {

            Box {
                CoilAsyncImage(
                    imageUrl = movieVo.posterPath,
                    modifier = modifier
                        .height(220.dp)
                        .width(180.dp)
                        .sharedElement(
                            state = rememberSharedContentState(
                                key = AppConstant.ComingSoonMoviesKey.format(movieVo.id)
                            ),
                            animatedVisibilityScope = animatedContentScope,
                        )
                        .clip(Shapes.small)
                )
                MovieFavoriteIcon(
                    modifier = Modifier.align(Alignment.TopEnd),
                    isFavorite = movieVo.isFavorite,
                ) {
                    onAction(UiEvent.FavoriteMovie(movieVo.id))
                }
            }

            Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

            Text(
                text = movieVo.title,
                fontSize = Dimens.TEXT_REGULAR_2,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_video_info), "")
                Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = movieVo.genreIds.joinToString(", "), fontSize = Dimens.TEXT_SMALL,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_calendar), "")
                Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                Text(text = movieVo.releaseDate, fontSize = Dimens.TEXT_SMALL)
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
    indicatorColor: Color = ColorPrimary,
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

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeNavPageNightPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true, label = ""
            ) {
                Surface {
                    HomePageContent(
                        modifier = Modifier,
                        uiState = UiState(
                            nowPlayingMovies = ResultState.Success(MovieVo.fakeMovies),
                            comingSoonMovies = ResultState.Success(MovieVo.fakeMovies),
                            popularMovies = ResultState.Success(MovieVo.fakeMovies),
                            popularActors = ResultState.Success(ActorVo.fakeActors)
                        ),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeNavPagePreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true, label = ""
            ) {
                Surface {
                    HomePageContent(
                        modifier = Modifier,
                        uiState = UiState(
                            nowPlayingMovies = ResultState.Success(MovieVo.fakeMovies),
                            comingSoonMovies = ResultState.Success(MovieVo.fakeMovies),
                            popularMovies = ResultState.Success(MovieVo.fakeMovies),
                            popularActors = ResultState.Success(ActorVo.fakeActors)
                        ),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun HomePageShimmerPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            NowPlayingMoviesShimmer()
            ComingSoonMoviesShimmer()
            PromotionAndDiscountShimmer()
        }

    }
}