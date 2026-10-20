package com.example.ui.screens.admin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.ui.StayEaseViewModel
import com.example.ui.components.AppDestination
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.IvoryBackground
import com.example.ui.theme.IvoryCard
import com.example.ui.theme.IvoryMuted
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.WarningAmber

@Composable
fun BookingsCalendarScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AppDestination.ADMIN_DASHBOARD)
    }

    val bookings by viewModel.bookings.collectAsState()
    val rooms by viewModel.rooms.collectAsState()

    val daysRange = (20..29).toList() // 10 day visible window
    val horizontalScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("bookings_calendar_screen")
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { viewModel.navigateTo(AppDestination.ADMIN_DASHBOARD) },
                    modifier = Modifier.testTag("calendar_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NavyPrimary
                    )
                }

                Column(modifier = Modifier.padding(start = 6.dp)) {
                    Text(
                        text = "Bookings Timeline Calendar",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cross-Room Master Schedule • October 2026",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted
                    )
                }
            }
        }

        // Color-coded Status Legend Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = IvoryCard)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CalendarLegend(color = GoldAccent, label = "Confirmed")
                CalendarLegend(color = SuccessGreen, label = "Checked-In (Active)")
                CalendarLegend(color = SlateMuted, label = "Completed")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Matrix Calendar View (Rooms on Y-axis, Dates on X-axis)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = IvoryCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Fixed Room Column
                Column(
                    modifier = Modifier
                        .width(96.dp)
                        .background(IvoryMuted)
                ) {
                    // Header corner
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "ROOM",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                    }
                    Divider(color = SlateBorder)

                    // Room items
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(rooms) { room ->
                            Box(
                                modifier = Modifier
                                    .height(72.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Column {
                                    Text(
                                        text = "${room.roomNumber}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyPrimary
                                    )
                                    Text(
                                        text = room.type.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SlateMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                            Divider(color = SlateBorder)
                        }
                    }
                }

                // Scrollable Days Columns
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(horizontalScrollState)
                ) {
                    // Dates Header Row
                    Row(
                        modifier = Modifier
                            .height(48.dp)
                            .background(NavyPrimary),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        daysRange.forEach { day ->
                            val isToday = day == 20
                            Box(
                                modifier = Modifier
                                    .width(76.dp)
                                    .padding(horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "OCT $day",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isToday) GoldAccent else Color.White,
                                        fontSize = 11.sp
                                    )
                                    if (isToday) {
                                        Text(
                                            text = "TODAY",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldLight,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Timeline Rows for Each Room
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(rooms) { room ->
                            Box(
                                modifier = Modifier
                                    .height(72.dp)
                                    .fillMaxWidth()
                            ) {
                                // Background grid columns
                                Row(modifier = Modifier.fillMaxSize()) {
                                    daysRange.forEach { day ->
                                        Box(
                                            modifier = Modifier
                                                .width(76.dp)
                                                .height(72.dp)
                                                .background(
                                                    if (day == 20) GoldContainer.copy(alpha = 0.2f) else Color.Transparent
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .width(1.dp)
                                                    .fillMaxSize()
                                                    .background(SlateBorder.copy(alpha = 0.5f))
                                                    .align(Alignment.CenterEnd)
                                            )
                                        }
                                    }
                                }

                                // Overlay Room Booking Blocks
                                val roomBookings = bookings.filter { it.roomId == room.id && it.status != BookingStatus.CANCELLED }
                                roomBookings.forEach { b ->
                                    val startOffsetDays = b.checkInDayIndex - 20
                                    val leftMargin = (startOffsetDays * 76).coerceAtLeast(0)
                                    val blockWidth = (b.stayDurationDays * 76) - 8

                                    val (blockBg, blockFg) = when (b.status) {
                                        BookingStatus.CHECKED_IN -> SuccessGreen to Color.White
                                        BookingStatus.CONFIRMED -> GoldAccent to NavyDark
                                        else -> SlateMuted to Color.White
                                    }

                                    Surface(
                                        modifier = Modifier
                                            .padding(start = leftMargin.dp, top = 8.dp)
                                            .width(blockWidth.dp)
                                            .height(54.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                viewModel.openBookingDetail(b.id, fromAdmin = true)
                                            }
                                            .testTag("timeline_block_${b.bookingRef}"),
                                        shape = RoundedCornerShape(8.dp),
                                        color = blockBg,
                                        shadowElevation = 3.dp
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = b.guestName,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = blockFg,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = "${b.bookingRef} • ${b.status.label}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = blockFg.copy(alpha = 0.9f),
                                                fontSize = 9.sp
                                            )
                                        }
                                    }
                                }
                            }
                            Divider(color = SlateBorder)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
fun CalendarLegend(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = NavyPrimary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}
