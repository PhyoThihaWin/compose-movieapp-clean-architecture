package com.pthw.composemovieappcleanarchitecture.feature.moviedetail

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pthw.appbase.utils.ResultRender
import com.pthw.appbase.utils.ResultState
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.StarRatingBar
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.feature.cinemaseat.navigateToCinemaSeatPage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColors
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalNavController
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.movie.model.MovieCastVo
import com.pthw.domain.movie.model.MovieDetailVo
import com.pthw.shared.extension.minutesToHoursAndMinutes
import com.pthw.shared.extension.roundTo

/**
 * Created by P.T.H.W on 08/04/2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailPage(
    modifier: Modifier = Modifier,
    navController: NavController = LocalNavController.current,
    viewModel: MovieDetailPageViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val uiState = UiState(
        key = viewModel.navArgs.sharedKey,
        backdropPath = viewModel.navArgs.backdropPath,
        movieDetailVo = viewModel.movieDetails.value
    )

    PageContent(
        modifier = modifier,
        uiState = uiState,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onAction = {
            when (it) {
                UiEvent.Continue -> navController.navigateToCinemaSeatPage()
                UiEvent.GoBack -> navController.popBackStack()
            }
        }
    )
}

private data class UiState(
    val key: String,
    val backdropPath: String,
    val movieDetailVo: ResultState<MovieDetailVo> = ResultState.Idle,
)

private sealed class UiEvent {
    data object GoBack : UiEvent()
    data object Continue : UiEvent()
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PageContent(
    modifier: Modifier,
    uiState: UiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onAction: (UiEvent) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val isScrolledOnTop by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset == 0 }
    }
    val bgImageHeight = LocalConfiguration.current.screenHeightDp / 3.6

    val progress by animateFloatAsState(
        targetValue = if (isScrolledOnTop) 0f else 1f,
        tween(500, easing = LinearOutSlowInEasing), label = ""
    )

    val constraintSet = getConstraintSet(bgImageHeight)

    MotionLayout(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        start = constraintSet.first,
        end = constraintSet.second,
        progress = progress
    ) {
        with(sharedTransitionScope) {
            CoilAsyncImage(
                imageUrl = uiState.backdropPath,
                modifier = modifier
                    .layoutId("poster")
                    .fillMaxWidth()
                    .height(bgImageHeight.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "${uiState.key}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = Dimens.MARGIN_20,
                            bottomStart = Dimens.MARGIN_20
                        )
                    )
            )
        }

        Icon(
            painterResource(id = R.drawable.ic_arrow_left),
            tint = Color.White,
            modifier = Modifier
                .layoutId("back_arrow")
                .padding(
                    start = Dimens.MARGIN_MEDIUM_2,
                    top = Dimens.MARGIN_MEDIUM_2
                )
                .clip(Shapes.small)
                .background(color = Color.Black.copy(0.3f))
                .clickable {
                    onAction(UiEvent.GoBack)
                }
                .padding(
                    vertical = Dimens.MARGIN_MEDIUM,
                    horizontal = Dimens.MARGIN_MEDIUM
                )
                .size(Dimens.MARGIN_LARGE),
            contentDescription = null
        )

        AnimatedVisibility(
            modifier = modifier.layoutId("content"),
            visible = uiState.movieDetailVo is ResultState.Success,
            enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ) {
            ResultRender(state = uiState.movieDetailVo) { movieDetail ->
                LazyColumn(
                    state = listState
                ) {
                    item {
                        MovieDetailInfoCardSection(movieDetail)

                        Column(
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
                        ) {
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM_2))

                            MovieInfoDescriptionTexts(
                                title = "Movie genre:",
                                text = movieDetail.genres.map { it.name }.joinToString(", ")
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                            MovieInfoDescriptionTexts(
                                title = "Censorship:",
                                text = if (movieDetail.adult) "18+" else "10+"
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                            MovieInfoDescriptionTexts(
                                title = "Language:",
                                text = "English"
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))

                            TitleTextView(
                                text = "Storyline",
                                modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))

                            // overview text with read more
                            Text(
                                text = buildAnnotatedString {
                                    if (movieDetail.overview.length > 250) {
                                        append(movieDetail.overview.substring(0..250))
                                        append("... ")
                                        withStyle(style = SpanStyle(color = ColorPrimary)) {
                                            append("See more")
                                        }
                                    } else {
                                        append(movieDetail.overview)
                                    }
                                },
                                modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                                fontSize = Dimens.TEXT_REGULAR,
                            )

                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))

                            TitleTextView(
                                text = "Casts",
                                modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                            ) {
                                items(movieDetail.casts.size) {
                                    MovieDetailActorListItem(movieDetail.casts[it])
                                }
                            }

                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))

                            TitleTextView(
                                text = "Crews",
                                modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_MEDIUM))
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = Dimens.MARGIN_MEDIUM_2)
                            ) {
                                items(movieDetail.crews.size) {
                                    MovieDetailActorListItem(movieDetail.crews[it])
                                }
                            }

                            Spacer(
                                modifier =
                                Modifier.padding(bottom = Dimens.MARGIN_LARGE)
                            )

                            TitleTextView(
                                text = "Cinema",
                                modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                            )
                            Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_10))
                        }


                    }

                    // payment list items
                    items(3) {
                        MovieDetailPaymentListItem()
                    }

                    item {
                        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_LARGE))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                                .height(Dimens.BTN_COMMON_HEIGHT),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                            onClick = {
                                onAction(UiEvent.Continue)
                            }) {
                            Text(
                                text = "Continue",
                                fontSize = Dimens.TEXT_REGULAR_2,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(bottom = Dimens.MARGIN_LARGE))
                    }

                }
            }
        }


        Box(modifier = Modifier.layoutId("loading")) {
            AnimatedVisibility(
                visible = uiState.movieDetailVo is ResultState.Loading,
            ) {
                CircularProgressIndicator()
            }
        }

    }
}

private fun getConstraintSet(posterHeight: Double): Pair<ConstraintSet, ConstraintSet> {
    val startConstraintSet = ConstraintSet {
        val poster = createRefFor("poster")
        constrain(poster) {
            alpha = 1f
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        val backArrow = createRefFor("back_arrow")
        constrain(backArrow) {
            top.linkTo(poster.top)
            start.linkTo(poster.start)
        }
        val content = createRefFor("content")
        constrain(content) {
            height = Dimension.fillToConstraints
            top.linkTo(poster.bottom, (-posterHeight / 4.5).dp)
            bottom.linkTo(parent.bottom)
        }
        val loading = createRefFor("loading")
        constrain(loading) {
            centerTo(parent)
        }
    }

    val endConstraintSet = ConstraintSet {
        val poster = createRefFor("poster")
        constrain(poster) {
            alpha = 0f
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.top, 0.dp)
        }
        val backArrow = createRefFor("back_arrow")
        constrain(backArrow) {
            top.linkTo(poster.top)
            start.linkTo(poster.start)
        }
        val content = createRefFor("content")
        constrain(content) {
            height = Dimension.fillToConstraints
            top.linkTo(parent.top, 0.dp)
            bottom.linkTo(parent.bottom)
        }
        val loading = createRefFor("loading")
        constrain(loading) {
            centerTo(parent)
        }
    }

    return Pair(startConstraintSet, endConstraintSet)
}

@Composable
private fun MovieDetailPaymentListItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = Dimens.MARGIN_MEDIUM_2,
                start = Dimens.MARGIN_MEDIUM_2,
                end = Dimens.MARGIN_MEDIUM_2
            )
            .border(
                width = 1.dp,
                color = ColorPrimary,
                shape = RoundedCornerShape(Dimens.MARGIN_MEDIUM_2)
            )
            .padding(
                horizontal = Dimens.MARGIN_MEDIUM_2,
                vertical = Dimens.MARGIN_MEDIUM_2
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vincom Ocean Park CGV",
                fontSize = Dimens.TEXT_REGULAR_2,
                fontWeight = FontWeight.Medium
            )
            Image(
                painter = painterResource(id = R.drawable.ic_payment_icon),
                contentDescription = ""
            )
        }
        Text(
            text = "9.32 km 27 Co Linh, Long Bien, Ha Noi",
            fontSize = Dimens.TEXT_SMALL
        )
    }
}

@Composable
private fun MovieDetailActorListItem(
    movieCastVo: MovieCastVo
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = Dimens.MARGIN_MEDIUM)
            .clip(Shapes.medium)
            .background(color = LocalCustomColors.current.cardBackgroundColor)
            .padding(
                vertical = Dimens.MARGIN_MEDIUM,
                horizontal = Dimens.MARGIN_MEDIUM_2
            )
            .width((LocalConfiguration.current.screenWidthDp / 3).dp)
    ) {
        CoilAsyncImage(
            imageUrl = movieCastVo.profilePath,
            modifier = Modifier
                .clip(CircleShape)
                .size(Dimens.MARGIN_XXXLARGE)
        )

        Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
        Text(
            text = movieCastVo.name,
            fontSize = Dimens.TEXT_REGULAR,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun MovieInfoDescriptionTexts(
    title: String,
    text: String
) {
    Row(
        modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
    ) {
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
private fun MovieDetailInfoCardSection(
    movieDetail: MovieDetailVo
) {
    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
            .clip(Shapes.medium)
            .fillMaxWidth()
            .background(color = LocalCustomColors.current.cardBackgroundColor)
            .padding(Dimens.MARGIN_MEDIUM_2)
    ) {
        TitleTextView(text = movieDetail.title, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_SMALL))
        Text(
            text = "${movieDetail.runtime.minutesToHoursAndMinutes()} • ${movieDetail.releaseDate}",
            color = Color.Gray,
            fontSize = Dimens.TEXT_REGULAR
        )
        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_MEDIUM_2))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Review")
            Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_SMALL))
            Icon(
                imageVector = Icons.Rounded.Star,
                tint = ColorPrimary,
                modifier = Modifier.size(Dimens.MARGIN_18),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = movieDetail.voteAverage.roundTo(1).toString(),
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


        var rating by remember { mutableStateOf(movieDetail.voteAverage.toFloat()) } // default rating will be 1
        StarRatingBar(
            maxStars = 10,
            rating = rating,
            starSize = Dimens.MARGIN_LARGE + Dimens.MARGIN_SMALL,
            starSpacing = 0.dp,
            onRatingChanged = {
//                rating = it
            }
        )

        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_MEDIUM_2))
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                ),
            horizontalArrangement = Arrangement.Center
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

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = true, label = ""
            ) {
                Surface {
                    PageContent(
                        modifier = Modifier,
                        uiState = UiState(
                            key = "",
                            backdropPath = "",
                            movieDetailVo = ResultState.Success(MovieDetailVo.fake())
                        ),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this,
                    )
                }
            }

        }
    }
}