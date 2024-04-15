package com.pthw.composemovieappcleanarchitecture.feature.cinemaseat

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.composable.TopAppBarView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorCinemaSeatReserved
import com.pthw.composemovieappcleanarchitecture.ui.theme.ColorPrimary
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.LocalCustomColorsPalette
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes

/**
 * Created by P.T.H.W on 14/04/2024.
 */

@Composable
fun CinemaSeatPage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    PageContent(modifier = modifier,
        onAction = {
            when (it) {
                UiEvent.GoBack -> navController.popBackStack()
                UiEvent.Continue -> TODO()
            }
        }
    )
}

private sealed class UiEvent {
    data object GoBack : UiEvent()
    data object Continue : UiEvent()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PageContent(
    modifier: Modifier,
    onAction: (UiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarView("Select Seat") {
                onAction(UiEvent.GoBack)
            }
        },
    ) { innerPadding ->
        val reservedSeats = listOf(50, 51, 78)
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_LARGE))
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.ic_cinema_screen),
                        alpha = 0.7f,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_MEDIUM_2))
                }

                item {
                    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 10)
                    FlowRow {
                        for (i in 1..100) {
                            val reserved = reservedSeats.contains(i)
                            val isSeatSelected = remember { mutableStateOf(false) }

                            Box(
                                modifier = Modifier
                                    .size(itemSize)
                                    .padding(Dimens.MARGIN_XSMALL)
                                    .clip(RoundedCornerShape(Dimens.MARGIN_SMALL))
                                    .background(
                                        color = if (reserved) ColorCinemaSeatReserved else {
                                            if (isSeatSelected.value) ColorPrimary
                                            else Color.DarkGray
                                        }
                                    )
                                    .clickable(
                                        enabled = !reserved
                                    ) {
                                        isSeatSelected.value = !isSeatSelected.value
                                    }
                            ) {
                                Text(
                                    text = "A${i % 10}",
                                    fontSize = Dimens.TEXT_SMALL,
                                    modifier = Modifier.align(Alignment.Center),
                                )
                            }
                        }
                    }

                    // seat info tips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.MARGIN_MEDIUM_2),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SeatInfoTipDesc(color = Color.DarkGray, text = "Available")
                        SeatInfoTipDesc(color = ColorCinemaSeatReserved, text = "Reserved")
                        SeatInfoTipDesc(color = ColorPrimary, text = "Selected")
                    }

                    Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_XLARGE))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TitleTextView(
                            text = "Select Date & Time",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }


                    // date
                    LazyRow(
                        contentPadding = PaddingValues(
                            horizontal = Dimens.MARGIN_MEDIUM_2,
                            vertical = Dimens.MARGIN_MEDIUM_2
                        )
                    ) {
                        items(10) {
                            DateListItem()
                        }
                    }

                    // time
                    LazyRow(
                        contentPadding = PaddingValues(
                            horizontal = Dimens.MARGIN_MEDIUM_2,
                            vertical = Dimens.MARGIN_MEDIUM_2
                        )
                    ) {
                        items(10) {
                            TimeListItem()
                        }
                    }

                    Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_LARGE))
                }
            }
            Divider(color = Color.DarkGray)
            TotalAndBuyTicketSection()
        }
    }

}

@Composable
private fun TotalAndBuyTicketSection() {
    Row(
        modifier = Modifier.padding(
            horizontal = Dimens.MARGIN_MEDIUM_2,
            vertical = Dimens.MARGIN_20
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(text = "Total", fontSize = Dimens.TEXT_SMALL)
            Text(
                text = "210.000 VND",
                fontSize = Dimens.TEXT_XLARGE,
                color = ColorPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .height(Dimens.BTN_COMMON_HEIGHT),
            onClick = { /*TODO*/ }) {
            Text(text = "But ticket", fontSize = Dimens.TEXT_REGULAR_2)
        }
    }
}

@Composable
private fun TimeListItem() {
    Box(
        modifier = Modifier
            .padding(end = Dimens.MARGIN_MEDIUM_2)
            .clip(Shapes.large)
            .background(color = LocalCustomColorsPalette.current.cardBackgroundColor)
            .border(
                width = 1.dp,
                color = ColorPrimary,
                shape = Shapes.large
            )
            .padding(
                horizontal = Dimens.MARGIN_LARGE,
                vertical = Dimens.MARGIN_MEDIUM
            ),
    ) {
        Text(
            text = "14:15",
            fontSize = Dimens.TEXT_REGULAR_2,
        )
    }
}

@Composable
private fun DateListItem() {
    Column(
        modifier = Modifier
            .padding(end = Dimens.MARGIN_MEDIUM)
            .clip(Shapes.extraLarge)
            .background(color = ColorPrimary)
            .padding(
                start = Dimens.MARGIN_6,
                end = Dimens.MARGIN_6,
                top = Dimens.MARGIN_MEDIUM_2,
                bottom = Dimens.MARGIN_6
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dec",
            fontSize = Dimens.TEXT_REGULAR_3,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(top = Dimens.MARGIN_MEDIUM_2))
        Box(
            modifier = Modifier
                .size(Dimens.MARGIN_XXXLARGE)
                .clip(CircleShape)
                .background(Color.Black)
                .padding(Dimens.MARGIN_MEDIUM)
        ) {
            Text(
                text = "10",
                fontSize = Dimens.TEXT_REGULAR_3,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun SeatInfoTipDesc(
    color: Color,
    text: String
) {
    Row {
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.MARGIN_SMALL))
                .background(color = color)
                .size(Dimens.MARGIN_LARGE),
        )
        Spacer(modifier = Modifier.padding(end = Dimens.MARGIN_MEDIUM))
        Text(text = text, fontSize = Dimens.TEXT_REGULAR)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:parent=pixel_5"
)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        PageContent(modifier = Modifier)
    }
}