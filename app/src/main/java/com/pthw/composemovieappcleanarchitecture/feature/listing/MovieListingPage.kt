package com.pthw.composemovieappcleanarchitecture.feature.listing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.appbase.viewstate.RenderCompose
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColorsPalette
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.model.ActorVo
import com.pthw.domain.model.MovieVo

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Composable
fun MovieListingPage(
    modifier: Modifier = Modifier,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    val uiState = UiState(
        nowPlayingMovies = viewModel.nowPlayingMovies.value,
    )
    PageContent(modifier = modifier, uiState = uiState)
}

private data class UiState(
    val nowPlayingMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val comingSoonMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val popularMovies: ObjViewState<List<MovieVo>> = ObjViewState.Idle(),
    val popularActors: ObjViewState<List<ActorVo>> = ObjViewState.Idle()
)

@Composable
private fun PageContent(
    modifier: Modifier,
    uiState: UiState
) {
    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .background(color = Color.Black)
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2, vertical = Dimens.MARGIN_MEDIUM_2)
            ) {
                Column(modifier.weight(1f)) {
                    Text("Movies", fontSize = Dimens.TEXT_REGULAR_3, fontWeight = FontWeight.Medium)
                }
            }
        },
    ) { innerPadding ->
        RenderCompose(state = uiState.nowPlayingMovies,
            loading = {
                CircularProgressIndicator()
            },
            success = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.padding(innerPadding),
                    contentPadding = PaddingValues(
                        horizontal = Dimens.MARGIN_MEDIUM,
                        vertical = Dimens.MARGIN_MEDIUM_2
                    ),
                    verticalArrangement = Arrangement.spacedBy(Dimens.MARGIN_20),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(it.size) { index ->
                        MoviesItemView(modifier = modifier, movieVo = it[index])
                    }
                }
            })
    }
}


@Composable
private fun MoviesItemView(
    modifier: Modifier,
    movieVo: MovieVo
) {
    Column(
        modifier = modifier
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
            color = PrimaryColor,
            maxLines = 3,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        PageContent(modifier = Modifier, uiState = UiState())
    }
}