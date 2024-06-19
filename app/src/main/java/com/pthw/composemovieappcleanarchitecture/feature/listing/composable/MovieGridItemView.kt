package com.pthw.composemovieappcleanarchitecture.feature.listing.composable

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.SharedAnimatedContent
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.home.model.MovieVo
import com.pthw.shared.extension.roundTo
import com.pthw.shared.extension.simpleClickable

/**
 * Created by P.T.H.W on 28/04/2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieGridItemView(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    movieVo: MovieVo,
    itemClick: () -> Unit
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier
                .sharedElement(
                    state = rememberSharedContentState(
                        key = "image-${movieVo.id}"
                    ),
                    animatedVisibilityScope = animatedContentScope,
                )
                .simpleClickable {
                    itemClick()
                }
        ) {
            CoilAsyncImage(
                imageUrl = movieVo.posterPath,
                modifier = modifier
                    .height(240.dp)
                    .clip(Shapes.small)
            )

            Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))

            Text(
                text = movieVo.title,
                fontSize = Dimens.TEXT_REGULAR_2,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.padding(top = Dimens.MARGIN_MEDIUM))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    tint = ColorPrimary,
                    modifier = Modifier.size(Dimens.MARGIN_18),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
                Text(
                    text = movieVo.voteAverage.roundTo(1).toString(),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_SMALL))
                Text(
                    text = "(1.222)",
                    color = Color.Gray,
                    fontSize = Dimens.TEXT_XSMALL,
                )
            }

            Row(
                verticalAlignment = Alignment.Top
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_video_info), "")
                Spacer(modifier = modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = movieVo.genreIds.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun MovieGridItemViewPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            SharedAnimatedContent {
                MovieGridItemView(
                    sharedTransitionScope = this,
                    animatedContentScope = it,
                    movieVo = MovieVo(
                        backdropPath = "/4MCKNAc6AbWjEsM2h9Xc29owo4z.jpg",
                        genreIds = listOf("Comedy", "Sc-fi"),
                        id = 866398,
                        overview = "One man's campaign for vengeance takes on national stakes after he is revealed to be a former operative of a powerful and clandestine organization known as Beekeepers.",
                        posterPath = "/A7EByudX0eOzlkQ2FIbogzyazm2.jpg",
                        releaseDate = "2024-01-08",
                        title = "The Beekeeper",
                        voteAverage = 7.461.toFloat(),
                    ),
                    itemClick = {}
                )
            }
        }
    }
}