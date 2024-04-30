package com.pthw.composemovieappcleanarchitecture.feature.movie

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pthw.composemovieappcleanarchitecture.composable.ErrorMessage
import com.pthw.composemovieappcleanarchitecture.composable.LoadingNextPageItem
import com.pthw.composemovieappcleanarchitecture.composable.PageLoader
import com.pthw.composemovieappcleanarchitecture.feature.listing.MovieListingPageViewModel
import com.pthw.composemovieappcleanarchitecture.feature.listing.composable.MovieGridItemView
import com.pthw.composemovieappcleanarchitecture.feature.moviedetail.navigateToMovieDetailPage
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalNavController
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes
import com.pthw.domain.home.model.MovieVo
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

/**
 * Created by P.T.H.W on 25/03/2024.
 */

private val tabLabels = listOf(
    "Now playing",
    "Coming soon",
)

@Composable
fun MovieNavPage(
    modifier: Modifier = Modifier,
    navController: NavController = LocalNavController.current,
    viewModel: MovieListingPageViewModel = hiltViewModel()
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    PageContent(
        modifier = modifier,
        tabIndex = tabIndex,
        nowPlayingPagingItems = viewModel.nowPlayingPagingFlow.collectAsLazyPagingItems(),
        upComingPagingItems = viewModel.upComingPagingFlow.collectAsLazyPagingItems(),
        onAction = {
            when (it) {
                is UiEvent.TapChanged -> tabIndex = it.index
                is UiEvent.ItemClick -> navController.navigateToMovieDetailPage(it.movie.id)
            }
        }
    )
}

private sealed class UiEvent {
    class TapChanged(val index: Int) : UiEvent()
    class ItemClick(val movie: MovieVo) : UiEvent()
}

@Composable
private fun PageContent(
    modifier: Modifier,
    tabIndex: Int = 0,
    nowPlayingPagingItems: LazyPagingItems<MovieVo>,
    upComingPagingItems: LazyPagingItems<MovieVo>,
    onAction: (UiEvent) -> Unit = {}

) {
    Scaffold { innerPadding ->
        val pagingItems = if (tabIndex == 0) nowPlayingPagingItems else upComingPagingItems

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

            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM_2))
                MovieTypeTabRow(
                    tabLabels = tabLabels,
                    tabIndex = tabIndex,
                    onTabSelected = {
                        onAction(UiEvent.TapChanged(it))
                    }
                )
            }

            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let {
                    MovieGridItemView(modifier = modifier, movieVo = it) {
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

@Composable
fun MovieTypeTabRow(
    tabLabels: List<String>,
    tabIndex: Int,
    onTabSelected: (index: Int) -> Unit
) {
    TabRow(
        modifier = Modifier
            .padding(vertical = Dimens.MARGIN_MEDIUM_2)
            .clip(Shapes.small)
            .background(color = Color.DarkGray)
            .padding(Dimens.MARGIN_SMALL)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() } // This is mandatory
            ) {},
        selectedTabIndex = tabIndex,
        containerColor = Color.DarkGray,
        indicator = {
            Box(
                Modifier
                    .tabIndicatorOffset(it[tabIndex])
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .background(color = ColorPrimary)
            )
        },

        divider = {}

    ) {
        tabLabels.forEachIndexed { index, title ->
            val selected = tabIndex == index
            Tab(
                modifier = Modifier.zIndex(2f),
                selected = selected,
                onClick = {
                    onTabSelected(index)
                },
                text = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = if (selected) Color.Black else Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            )
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        PageContent(
            modifier = Modifier,
            nowPlayingPagingItems = flowOf(PagingData.from(emptyList<MovieVo>())).collectAsLazyPagingItems(),
            upComingPagingItems = flowOf(PagingData.from(emptyList<MovieVo>())).collectAsLazyPagingItems()
        )
    }
}