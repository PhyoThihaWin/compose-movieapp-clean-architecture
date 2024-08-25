@file:OptIn(ExperimentalMaterial3Api::class)

package com.pthw.composemovieappcleanarchitecture.feature.profile

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.IconAndTextInfoRow
import com.pthw.composemovieappcleanarchitecture.feature.profile.composable.LanguageModalSheet
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColors
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalLocalization
import com.pthw.domain.general.Localization
import timber.log.Timber

/**
 * Created by P.T.H.W on 27/03/2024.
 */

@Composable
fun ProfileNavPage(
    viewModel: ProfileNavPageViewModel = hiltViewModel()
) {

    PageContent {
        when (it) {
            is UiEvent.LocaleSelected -> viewModel.updateLanguageCache(it.localeCode)
        }
    }
}


private sealed class UiEvent {
    data class LocaleSelected(val localeCode: String) : UiEvent()
}

@Composable
private fun PageContent(
    onAction: (UiEvent) -> Unit = {}
) {

    var toShowLanguageSheet by remember { mutableStateOf(false) }

    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                LocalLocalization.current.value

                Spacer(modifier = Modifier.height(Dimens.MARGIN_XLARGE))
                Row(
                    modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoilAsyncImage(
                            imageUrl = "https://img.freepik.com/free-photo/black-model-posing_23-2148171651.jpg",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                        Column {
                            Text(
                                text = "Angelina",
                                fontSize = Dimens.TEXT_HEADING_2,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(Dimens.MARGIN_10))
                            IconAndTextInfoRow(
                                painterResources = R.drawable.ic_call,
                                iconSize = Dimens.MARGIN_20,
                                text = "(704) 555-0127",
                                fontSize = Dimens.TEXT_REGULAR
                            )
                            IconAndTextInfoRow(
                                painterResources = R.drawable.ic_mail,
                                iconSize = Dimens.MARGIN_20,
                                text = "angelina@example.com",
                                fontSize = Dimens.TEXT_REGULAR
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile_edit),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.MARGIN_XXXLARGE))
                ProfileSettingItem(
                    painterResource = R.drawable.ic_ticket_profile_item,
                    text = stringResource(R.string.txt_my_ticket)
                )
                ProfileSettingItem(
                    painterResource = R.drawable.ic_shopping_cart_profile_item,
                    text = stringResource(R.string.txt_payment_history)
                )
                ProfileSettingItem(
                    painterResource = R.drawable.ic_translate_profile_item,
                    text = stringResource(R.string.txt_change_language)
                ) {
                    toShowLanguageSheet = true
                }
                ProfileSettingItem(
                    painterResource = R.drawable.ic_lock_profile_item,
                    text = stringResource(R.string.txt_change_password)
                )
                ProfileSettingItemWithToggle(
                    painterResource = R.drawable.ic_face_id_profile_item,
                    text = stringResource(R.string.txt_face_id_touch_id)
                )
                ProfileSettingItemWithToggle(
                    painterResource = R.drawable.ic_dark_mode,
                    text = stringResource(R.string.txt_dark_mode)
                )
            }
        }

        LanguageModalSheet(
            isShow = toShowLanguageSheet,
            localeCode = LocalConfiguration.current.locales[0].language
        ) {
            toShowLanguageSheet = false
            it?.let { localeCode ->
                onAction(UiEvent.LocaleSelected(localeCode))
            }
        }
    }
}

@Composable
fun ProfileSettingItem(
    @DrawableRes painterResource: Int,
    text: String,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = painterResource),
                modifier = Modifier.size(Dimens.MARGIN_XLARGE),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(Dimens.MARGIN_10))
            Text(
                text = text,
                fontSize = Dimens.TEXT_REGULAR_2,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null
            )

        }
        HorizontalDivider(color = LocalCustomColors.current.dividerColor)
    }
}

@Composable
fun ProfileSettingItemWithToggle(
    @DrawableRes painterResource: Int,
    text: String,
    onClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = painterResource),
                modifier = Modifier.size(Dimens.MARGIN_XLARGE),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(Dimens.MARGIN_10))
            Text(
                text = text,
                fontSize = Dimens.TEXT_REGULAR_2,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            SwitchWithCustomColors()
        }
        HorizontalDivider(color = LocalCustomColors.current.dividerColor)
    }
}

@Composable
fun SwitchWithCustomColors() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = ColorPrimary,
            checkedTrackColor = LocalCustomColors.current.searchBoxColor,
            checkedBorderColor = LocalCustomColors.current.searchBoxColor,
            uncheckedThumbColor = Color.LightGray,
            uncheckedTrackColor = LocalCustomColors.current.searchBoxColor,
            uncheckedBorderColor = LocalCustomColors.current.searchBoxColor,
        )
    )
}

@Preview
@Composable
private fun SwitchWithCustomColorsPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        SwitchWithCustomColors()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    fontScale = 1.0f, locale = "my"
)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        PageContent()
    }
}