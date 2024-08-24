@file:OptIn(ExperimentalMaterial3Api::class)

package com.pthw.composemovieappcleanarchitecture.feature.profile.composable

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pthw.composemovieappcleanarchitecture.AppConstant
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.domain.general.Localization

/**
 * Created by P.T.H.W on 24/08/2024.
 */

@Composable
fun LanguageModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    isShow: Boolean,
    localeCode: String,
    onDismiss: (locale: Localization?) -> Unit
) {
    if (!isShow) return

    ModalBottomSheet(
        onDismissRequest = { onDismiss(null) },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
        ) {
            TitleTextView(text = "Choose Language")
            Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))
            Text(text = "Which language do you want to use")

            Spacer(modifier = Modifier.height(Dimens.MARGIN_20))

            AppConstant.languageList.forEachIndexed { index, item ->
                LanguageRadioGroup(item, localeCode, onDismiss, index)
            }

            Spacer(modifier = Modifier.height(Dimens.BTN_COMMON_HEIGHT))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.BTN_COMMON_HEIGHT),
                onClick = { /*TODO*/ }) {
                Text(text = "Select", fontSize = Dimens.TEXT_REGULAR_2)
            }

            Spacer(modifier = Modifier.height(Dimens.MARGIN_20))
        }
    }
}

@Composable
private fun LanguageRadioGroup(
    item: Localization,
    localeCode: String,
    onDismiss: (locale: Localization?) -> Unit,
    index: Int
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .selectable(
                    selected = (item.localeCode == localeCode),
                    enabled = (item.localeCode != localeCode),
                    onClick = {
                        onDismiss(item)
                    },
                    role = Role.RadioButton
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = item.title),
                fontSize = Dimens.TEXT_REGULAR_3,
                fontWeight = FontWeight.Medium,
                color = if (item.localeCode == localeCode) ColorPrimary else Color.Black,
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

@Preview
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