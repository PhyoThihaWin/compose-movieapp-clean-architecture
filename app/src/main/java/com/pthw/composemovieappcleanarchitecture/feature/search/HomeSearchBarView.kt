package com.pthw.composemovieappcleanarchitecture.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColors
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes

@Composable
fun HomeSearchBarView(
    modifier: Modifier,
    hint: String,
    onValueChange: (String) -> Unit
) {
    var textSearchBox by rememberSaveable { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(color = LocalCustomColors.current.searchBoxColor)
            .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search_normal),
            contentDescription = ""
        )
        TextField(
            value = textSearchBox,
            onValueChange = {
                textSearchBox = it
                onValueChange(it)
            },
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = Dimens.TEXT_REGULAR
            ),
            placeholder = {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    fontSize = Dimens.TEXT_REGULAR
                )
            },
            trailingIcon = {
                AnimatedVisibility(textSearchBox.length > 1) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                textSearchBox = ""
                                onValueChange("")
                            }
                            .padding(Dimens.MARGIN_MEDIUM),
                        painter = painterResource(id = R.drawable.ic_delete_search),
                        contentDescription = ""
                    )
                }
            }
        )
    }

}