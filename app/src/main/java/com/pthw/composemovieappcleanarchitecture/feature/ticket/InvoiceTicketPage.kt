package com.pthw.composemovieappcleanarchitecture.feature.ticket

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pthw.composemovieappcleanarchitecture.R
import com.pthw.composemovieappcleanarchitecture.composable.CoilAsyncImage
import com.pthw.composemovieappcleanarchitecture.composable.DashedDivider
import com.pthw.composemovieappcleanarchitecture.composable.TitleTextView
import com.pthw.composemovieappcleanarchitecture.composable.TopAppBarView
import com.pthw.composemovieappcleanarchitecture.ui.theme.ComposeMovieAppCleanArchitectureTheme
import com.pthw.composemovieappcleanarchitecture.ui.theme.Dimens
import com.pthw.composemovieappcleanarchitecture.ui.theme.Shapes

/**
 * Created by P.T.H.W on 19/04/2024.
 */

@Composable
fun InvoiceTicketPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    PageContent(modifier,
        onAction = {
            when (it) {
                UiEvent.GoBack -> navController.popBackStack()
                UiEvent.Continue -> navController.popBackStack()
            }
        }
    )
}

private sealed class UiEvent {
    data object GoBack : UiEvent()
    data object Continue : UiEvent()
}

@Composable
private fun PageContent(
    modifier: Modifier,
    onAction: (UiEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarView("My Ticket") {
                onAction(UiEvent.GoBack)
            }
        },
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = Dimens.MARGIN_MEDIUM_2)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimens.MARGIN_MEDIUM_2)
                        .clip(Shapes.medium)
                        .background(color = MaterialTheme.colorScheme.surfaceContainer)
                        .padding(vertical = Dimens.MARGIN_MEDIUM_2)
                ) {
                    // info card
                    InvoiceMovieInfoDetailSection()

                    Spacer(modifier = Modifier.height(Dimens.MARGIN_XLARGE))

                    Row(
                        modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_invoice_calendar),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(Dimens.MARGIN_SMALL))
                        Column {
                            Text(
                                text = "14h15’",
                                fontSize = Dimens.TEXT_REGULAR,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "10.12.2022",
                                fontSize = Dimens.TEXT_REGULAR,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_seat_cinema),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(Dimens.MARGIN_SMALL))
                        Column {
                            Text(
                                text = "Section 4",
                                fontSize = Dimens.TEXT_REGULAR,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Seat H7, H8",
                                fontSize = Dimens.TEXT_REGULAR,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = Dimens.MARGIN_XLARGE,
                            horizontal = Dimens.MARGIN_MEDIUM_2,
                        )
                    )

                    Row(
                        modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_money_send),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                        Text(
                            text = "210.000 VND",
                            fontSize = Dimens.TEXT_REGULAR_2,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))

                    Row(
                        modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                        Column {
                            Text(
                                text = "Vincom Ocean Park",
                                fontSize = Dimens.TEXT_REGULAR_2,
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = "4th floor, Vincom Ocean Park, Da Ton, Gia Lam, Ha Noi",
                                fontSize = Dimens.TEXT_REGULAR,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))

                    Row(
                        modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_note),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                        Text(
                            text = "4th floor, Vincom Ocean Park, Da Ton, Gia Lam, Ha Noi",
                            fontSize = Dimens.TEXT_REGULAR,
                        )
                    }


                    CircleDotDashedSectionView()

                    Spacer(modifier = Modifier.height(Dimens.MARGIN_LARGE))

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.MARGIN_LARGE),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.img_barcode),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentDescription = ""
                    )

                    Text(
                        text = "Oder ID: 78889377726",
                        fontSize = Dimens.TEXT_REGULAR,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.MARGIN_MEDIUM),
                        textAlign = TextAlign.Center
                    )

                }
            }
        }
    }
}

@Composable
fun CircleDotDashedSectionView() {
    Box(
        modifier = Modifier,
    ) {
        DashedDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            color = Color.LightGray,
            dashWidth = Dimens.MARGIN_6,
            gapWidth = Dimens.MARGIN_6,
        )

        Spacer(
            modifier = Modifier
                .offset(x = -Dimens.MARGIN_LARGE)
                .size(Dimens.MARGIN_XXXLARGE)
                .clip(CircleShape)
                .align(Alignment.CenterStart)
        )

        Spacer(
            modifier = Modifier
                .offset(x = Dimens.MARGIN_LARGE)
                .size(Dimens.MARGIN_XXXLARGE)
                .clip(CircleShape)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun InvoiceMovieInfoDetailSection() {
    Row(
        modifier = Modifier.padding(horizontal = Dimens.MARGIN_MEDIUM_2)
    ) {
        CoilAsyncImage(
            imageUrl = "https://mlpnk72yciwc.i.optimole.com/cqhiHLc.IIZS~2ef73/w:auto/h:auto/q:75/https://bleedingcool.com/wp-content/uploads/2024/01/godzilla_x_kong_the_new_empire_ver5_xxlg.jpg",
            modifier = Modifier
                .clip(Shapes.medium)
                .height(180.dp)
                .weight(1f),
        )

        Spacer(modifier = Modifier.width(Dimens.MARGIN_12))

        Column(
            modifier = Modifier
                .weight(1.5f)
                .padding(top = Dimens.MARGIN_12)
        ) {
            TitleTextView(
                text = "Avengers: Infinity War",
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_video_info),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = "Adventure, Sci-fi", fontSize = Dimens.TEXT_SMALL,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_video_info),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = "Vincom Ocean Park CGV", fontSize = Dimens.TEXT_SMALL,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_video_info),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = "10.12.2022 • 14:15", fontSize = Dimens.TEXT_SMALL,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PageContentPreview() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            PageContent(modifier = Modifier)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PageContentPreviewNight() {
    ComposeMovieAppCleanArchitectureTheme {
        Surface {
            PageContent(modifier = Modifier)
        }
    }
}