package com.pthw.composemovieappcleanarchitecture.feature.listing

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.ErrorMessage
import com.pthw.composemovieappcleanarchitecture.composable.LoadingNextPageItem
import com.pthw.composemovieappcleanarchitecture.composable.PageLoader
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.PrimaryColor
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.flowOf

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@Composable
fun MovieListingPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    PageContent(
        modifier = modifier,
        pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems(),
        onBack = {
            navController.popBackStack()
        }
    )
}


@Composable
private fun PageContent(
    modifier: Modifier,
    pagingItems: LazyPagingItems<MovieVo>,
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .padding(vertical = Dimens.MARGIN_MEDIUM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.KeyboardArrowLeft,
                    modifier = Modifier
                        .clickable {
                            onBack()
                        }
                        .padding(
                            vertical = Dimens.MARGIN_MEDIUM,
                            horizontal = Dimens.MARGIN_MEDIUM
                        ),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                Text("Movies", fontSize = Dimens.TEXT_REGULAR_3, fontWeight = FontWeight.Medium)
            }
        },
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(
                start = Dimens.MARGIN_MEDIUM_2,
                end = Dimens.MARGIN_MEDIUM_2,
                top = Dimens.MARGIN_MEDIUM,
                bottom = Dimens.MARGIN_LARGE
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.MARGIN_20),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MARGIN_MEDIUM_2)
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { MoviesItemView(modifier = modifier, movieVo = it) }
            }
            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            PageLoader(modifier = Modifier)
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pagingItems.loadState.refresh as LoadState.Error
                        item(span = { GridItemSpan(2) }) {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            LoadingNextPageItem(modifier = Modifier)
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = pagingItems.loadState.append as LoadState.Error
                        item(span = { GridItemSpan(2) }) {
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