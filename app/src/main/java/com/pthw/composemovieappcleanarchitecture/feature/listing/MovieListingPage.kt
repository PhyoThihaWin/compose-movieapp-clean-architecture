package com.pthw.composemovieappcleanarchitecture.feature.listing

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.pthw.composemovieappcleanarchitecture.composable.SharedAnimatedContent
import com.pthw.composemovieappcleanarchitecture.composable.TopAppBarView
import com.pthw.composemovieappcleanarchitecture.feature.listing.composable.MovieGridItemView
import com.pthw.composemovieappcleanarchitecture.feature.moviedetail.navigateToMovieDetailPage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalNavController
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.flowOf

/**
 * Created by P.T.H.W on 04/04/2024.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieListingPage(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navController: NavController = LocalNavController.current,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    PageContent(
        modifier = modifier,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        pagingItems = viewModel.nowPlayingPagingFlow.collectAsLazyPagingItems(),
        onAction = {
            when (it) {
                is UiEvent.GoBack -> navController.popBackStack()
                is UiEvent.ItemClick -> navController.navigateToMovieDetailPage(
                    movieId = it.movie.id, backdropPath = it.movie.backdropPath
                )
            }
        }
    )
}

private sealed class UiEvent {
    data object GoBack : UiEvent()
    class ItemClick(val movie: MovieVo) : UiEvent()
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PageContent(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    pagingItems: LazyPagingItems<MovieVo>,
    onAction: (UiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarView(title = "Movies") {
                onAction(UiEvent.GoBack)
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
                pagingItems[index]?.let {
                    MovieGridItemView(
                        modifier = modifier,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        movieVo = it
                    ) {
                        onAction(UiEvent.ItemClick(it))
                    }
                }
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


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
            SharedAnimatedContent {
                PageContent(
                    modifier = Modifier,
                    sharedTransitionScope = this,
                    animatedContentScope = it,
                    pagingItems = flowOf(PagingData.from(emptyList<MovieVo>())).collectAsLazyPagingItems()
                )
        }
    }
}