package com.pthw.composemovieappcleanarchitecture.feature.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.TopAppBarView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.domain.home.model.MovieVo
import com.pthw.shared.extension.roundTo

/**
 * Created by P.T.H.W on 25/03/2024.
 */

@Composable
fun FavoriteNavPage(
    viewModel: FavoriteNavPageViewModel = hiltViewModel()
) {
    val movies = viewModel.movies.collectAsState(initial = emptyList()).value
    PageContent(movies = movies)
}

@Composable
private fun PageContent(movies: List<MovieVo>) {
    Scaffold(
        topBar = {
            TopAppBarView(stringResource(id = R.string.txt_favorite_movies))
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(top = Dimens.MARGIN_MEDIUM, bottom = Dimens.BTN_COMMON_HEIGHT + Dimens.MARGIN_LARGE)
        ) {
            items(movies.size) {
                MovieListItemView(movies[it])
            }
        }
    }

}

@Composable
private fun MovieListItemView(
    movie: MovieVo,
) {
    Card(
        modifier = Modifier.padding(
            start = Dimens.MARGIN_20,
            end = Dimens.MARGIN_20,
            bottom = Dimens.MARGIN_MEDIUM_2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            CoilAsyncImage(
                imageUrl = movie.posterPath,
                modifier = Modifier
                    .aspectRatio(2f / 3f)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = Dimens.MARGIN_20,
                        horizontal = Dimens.MARGIN_MEDIUM_2
                    )
                    .weight(1f)

            ) {
                Text(
                    text = movie.title,
                    fontSize = Dimens.TEXT_REGULAR_2,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))


                // genre
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_video_info), "")
                    Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                    Text(
                        text = movie.genreIds.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // data
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_calendar), "")
                    Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                    Text(text = movie.releaseDate, fontSize = Dimens.TEXT_SMALL)
                }

                // star
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
                        text = movie.voteAverage.roundTo(1).toString(),
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
            }
        }
    }
}


@Preview
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            PageContent(movies = MovieVo.fakeMovies.take(4))
        }
    }
}