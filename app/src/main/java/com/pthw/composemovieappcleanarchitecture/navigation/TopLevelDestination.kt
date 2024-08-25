package com.pthw.composemovieappcleanarchitecture.navigation

import android.graphics.drawable.Icon
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.navigation.designsystem.NiaIcons

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home,
        iconTextId = R.string.nav_title_home,
        titleTextId = R.string.nav_title_home,
    ),
    TICKET(
        selectedIcon = R.drawable.ic_ticket_selected,
        unselectedIcon = R.drawable.ic_ticket,
        iconTextId = R.string.nav_title_ticket,
        titleTextId = R.string.nav_title_ticket,
    ),
    MOVIE(
        selectedIcon = R.drawable.ic_video_selected,
        unselectedIcon = R.drawable.ic_video,
        iconTextId = R.string.nav_title_movie,
        titleTextId = R.string.nav_title_movie,
    ),
    PROFILE(
        selectedIcon = R.drawable.ic_user_selected,
        unselectedIcon = R.drawable.ic_user,
        iconTextId = R.string.nav_title_profile,
        titleTextId = R.string.nav_title_profile,
    ),
}
