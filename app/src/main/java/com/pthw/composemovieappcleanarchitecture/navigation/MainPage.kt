package com.pthw.composemovieappcleanarchitecture.navigation

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.pthw.composemovieappcleanarchitecture.navigation.designsystem.NiaNavigationBar
import com.pthw.composemovieappcleanarchitecture.navigation.designsystem.NiaNavigationBarItem
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalLocalization
import timber.log.Timber
import java.util.Locale

/**
 * Created by P.T.H.W on 25/03/2024.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainPage(
    localeCode: String,
    windowSizeClass: WindowSizeClass,
    appState: MainPageState = rememberMainPageState(
        windowSizeClass = windowSizeClass,
    ),
) {
    val currentDestination = appState.currentDestination
    val destinations = appState.topLevelDestinations
    val context = LocalContext.current

    // update localization
    context.updateLocalization(localeCode)

    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination.isTopLevelDestinationInHierarchy(destinations),
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 400),
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = 400),
                    targetOffsetY = { it }
                )
            ) {
                NiaBottomBar(
                    destinations = destinations,
                    destinationsWithUnreadResources = emptySet(),
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = currentDestination,
                    modifier = Modifier.testTag("NiaBottomBar"),
                )
            }
        },
    ) { padding ->
        MainNavHost(
            modifier = Modifier.fillMaxSize(),
            appState = appState
        )
    }
}

private fun Context.updateLocalization(localeCode: String) {
    val beforeLocale = resources.configuration.locales[0]
    val locale = Locale(localeCode)
    resources.configuration.setLocale(locale)
    resources.updateConfiguration(resources.configuration, resources.displayMetrics)
    Timber.i("Locale, before: $beforeLocale, after: ${resources.configuration.locales[0]}")
}

@Composable
private fun NiaBottomBar(
    destinations: List<TopLevelDestination>,
    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    LocalLocalization.current
    NiaNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val hasUnread = destinationsWithUnreadResources.contains(destination)
            val selected = currentDestination.isNavigationBarItemSelected(destination)
            NiaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(destination.unselectedIcon),
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(destination.selectedIcon),
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(id = destination.iconTextId)) },
                modifier = if (hasUnread) Modifier.notificationDot() else Modifier,
            )
        }
    }
}

private fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }

private fun NavDestination?.isTopLevelDestinationInHierarchy(destinations: List<TopLevelDestination>) =
    this?.hierarchy?.any { destination ->
        destinations.map { it.route.javaClass.canonicalName }.contains(destination.route)
    } ?: false


private fun NavDestination?.isNavigationBarItemSelected(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route == destination.route.javaClass.canonicalName
    } ?: false


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NiaBottomBarPreview() {
    val testNav = NavDestination("")
    testNav.route = "home"
    ComposeMovieAppCleanArchitectureTheme {
        NiaBottomBar(
            destinations = TopLevelDestination.entries,
            destinationsWithUnreadResources = emptySet(),
            onNavigateToDestination = {},
            currentDestination = testNav,
            modifier = Modifier.testTag("NiaBottomBar"),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NiaBottomBarNightPreview() {
    val testNav = NavDestination("")
    testNav.route = "home"
    ComposeMovieAppCleanArchitectureTheme {
        NiaBottomBar(
            destinations = TopLevelDestination.entries,
            destinationsWithUnreadResources = emptySet(),
            onNavigateToDestination = {},
            currentDestination = testNav,
            modifier = Modifier.testTag("NiaBottomBar"),
        )
    }
}

