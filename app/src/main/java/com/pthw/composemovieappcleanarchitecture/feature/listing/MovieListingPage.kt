package com.pthw.composemovieappcleanarchitecture.feature.listing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pthw.appbase.viewstate.ObjViewState
import com.pthw.appbase.viewstate.RenderCompose
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.ErrorMessage
import com.pthw.composemovieappcleanarchitecture.composable.LoadingNextPageItem
import com.pthw.composemovieappcleanarchitecture.composable.PageLoader
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColorsPalette
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.home.model.ActorVo
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.flowOf

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Composable
fun MovieListingPage(
    modifier: Modifier = Modifier,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    PageContent(
        modifier = modifier,
        pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    )
}


@Composable
private fun PageContent(
    modifier: Modifier,
    pagingItems: LazyPagingItems<MovieVo>
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
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { MoviesItemView(modifier = modifier, movieVo = it) }
            }
            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = pagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
        }

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
        PageContent(
            modifier = Modifier,
            pagingItems = flowOf(PagingData.from(emptyList<MovieVo>())).collectAsLazyPagingItems()
        )
    }
}