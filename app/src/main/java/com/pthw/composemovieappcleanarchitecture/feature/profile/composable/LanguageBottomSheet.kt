@file:OptIn(ExperimentalMaterial3Api::class)

package com.pthw.composemovieappcleanarchitecture.feature.profile.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pthw.composemovieappcleanarchitecture.AppConstant
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.domain.general.Localization
import timber.log.Timber

/**
 * Created by P.T.H.W on 24/08/2024.
 */

@Composable
fun LanguageModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isShow: Boolean,
    localeCode: String,
    onDismiss: (localeCode: String?) -> Unit
) {
    if (!isShow) return

    var selectedLocaleCode by remember { mutableStateOf(localeCode) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss(null) },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column {
            TitleTextView(
                modifier = Modifier.padding(start = Dimens.MARGIN_MEDIUM_2),
                text = if (selectedLocaleCode == Localization.ENGLISH) {
                    stringResource(R.string.txt_choose_language)
                } else {
                    stringResource(R.string.txt_choose_language_mm)
                }
            )
            Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))
            Text(
                modifier = Modifier.padding(start = Dimens.MARGIN_MEDIUM_2),
                text = if (selectedLocaleCode == Localization.ENGLISH) {
                    stringResource(R.string.txt_language_change_desc)
                } else {
                    stringResource(R.string.txt_language_change_desc_mm)
                }
            )

            Spacer(modifier = Modifier.height(Dimens.MARGIN_20))

            // radio group
            LanguageRadioGroup(selectedLocaleCode) {
                selectedLocaleCode = it
            }

            Spacer(modifier = Modifier.height(Dimens.BTN_COMMON_HEIGHT))

            Button(
                modifier = Modifier
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                    .fillMaxWidth()
                    .height(Dimens.BTN_COMMON_HEIGHT),
                onClick = {
                    onDismiss(selectedLocaleCode)
                }) {
                Text(
                    text = if (selectedLocaleCode == Localization.ENGLISH) {
                        stringResource(R.string.txt_select)
                    } else {
                        stringResource(R.string.txt_select_mm)
                    }, fontSize = Dimens.TEXT_REGULAR_2
                )
            }

            Spacer(modifier = Modifier.height(Dimens.MARGIN_20))
        }
    }
}

@Composable
private fun LanguageRadioGroup(
    localeCode: String,
    onDismiss: (localeCode: String) -> Unit,
) {
    AppConstant.languageList.forEachIndexed { index, item ->
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (item.localeCode == localeCode),
                        enabled = (item.localeCode != localeCode),
                        onClick = {
                            onDismiss(item.localeCode)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = item.title),
                    fontSize = Dimens.TEXT_REGULAR_3,
                    fontWeight = FontWeight.Medium,
                    color = if (item.localeCode == localeCode) ColorPrimary else Color.Unspecified,
                    modifier = Modifier.weight(1f)
                )
                RadioButton(
                    selected = (item.localeCode == localeCode),
                    onClick = null
                )
            }
            if (index == 0) {
                HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.MARGIN_SMALL))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun LanguageBottomSheetPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            LanguageModalSheet(
                sheetState = rememberStandardBottomSheetState(),
                isShow = true,
                localeCode = Localization.ENGLISH
            ) {}
        }
    }
}