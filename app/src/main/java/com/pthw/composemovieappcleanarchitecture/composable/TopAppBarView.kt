package com.pthw.composemovieappcleanarchitecture.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens

/**
 * Created by P.T.H.W on 15/04/2024.
 */

@Composable
fun TopAppBarView(
    title: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(vertical = Dimens.MARGIN_MEDIUM),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            modifier = Modifier
                .clickable {
                    onBack()
                }
                .padding(
                    vertical = Dimens.MARGIN_XSMALL,
                    horizontal = Dimens.MARGIN_MEDIUM
                )
                .align(Alignment.CenterStart)
                .size(Dimens.MARGIN_XLARGE),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
        Text(
            title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = Dimens.TEXT_REGULAR_3, fontWeight = FontWeight.Medium
        )
    }
}