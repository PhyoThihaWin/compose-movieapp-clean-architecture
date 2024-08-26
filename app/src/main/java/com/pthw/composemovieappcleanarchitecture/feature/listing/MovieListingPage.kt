package com.pthw.composemovieappcleanarchitecture.feature.listing

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pthw.composemovieappcleanarchitecture.AppConstant
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.ErrorMessage
import com.pthw.composemovieappcleanarchitecture.composable.LoadingNextPageItem
import com.pthw.composemovieappcleanarchitecture.composable.PageEmpty
import com.pthw.composemovieappcleanarchitecture.composable.PageLoader
import com.pthw.composemovieappcleanarchitecture.composable.SharedAnimatedContent
import com.pthw.composemovieappcleanarchitecture.composable.TopAppBarView
import com.pthw.composemovieappcleanarchitecture.feature.listing.composable.MovieGridItemView
import com.pthw.composemovieappcleanarchitecture.feature.moviedetail.navigateToMovieDetailPage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.domain.home.model.MovieVo
import com.pthw.shared.extension.or
import com.pthw.shared.extension.then
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
    navController: NavController,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    val title = (viewModel.movieType == MovieListingPageNavigation.NOW_PLAYING) then
            stringResource(id = R.string.txt_now_playing) or stringResource(id = R.string.txt_coming_soon)

    PageContent(
        modifier = modifier,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        title = title,
        pagingItems = viewModel.getMovieListingFlow().collectAsLazyPagingItems(),
        onAction = {
            when (it) {
                is UiEvent.GoBack -> navController.popBackStack()
                is UiEvent.ItemClick -> navController.navigateToMovieDetailPage(
                    sharedKey = AppConstant.ListingMoviesKey.format(it.movie.id),
                    movie = it.movie
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
    title: String,
    pagingItems: LazyPagingItems<MovieVo>,
    onAction: (UiEvent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarView(title = title) {
                onAction(UiEvent.GoBack)
            }
        },
    ) { innerPadding ->
        var pagingLoadState by remember { mutableStateOf<CombinedLoadStates?>(null) }

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
                pagingLoadState = loadState
                when {
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

        // loadState handle
        when {
            pagingLoadState?.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 ->
                PageEmpty(modifier.fillMaxSize())

            pagingLoadState?.refresh is LoadState.Loading && pagingItems.itemCount == 0 -> PageLoader(
                modifier.fillMaxSize()
            )
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
                title = "Movies",
                pagingItems = flowOf(PagingData.from(emptyList<MovieVo>())).collectAsLazyPagingItems()
            )
        }
    }
}