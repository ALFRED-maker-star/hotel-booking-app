package com.example.ui.screens.guest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.StayEaseViewModel
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

@Composable
fun BookRoomScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val room = viewModel.getSelectedRoom() ?: return
    val draft by viewModel.bookingDraft.collectAsState()

    var startDay by remember { mutableIntStateOf(24) }
    var endDay by remember { mutableIntStateOf(27) }
    var guestsCount by remember { mutableIntStateOf(draft.guestsCount) }

    val totalNights = (endDay - startDay).coerceAtLeast(1)
    val roomRate = room.pricePerNight * totalNights
    val resortFees = 45.0 * totalNights
    val taxes = (roomRate + resortFees) * 0.12
    val grandTotal = roomRate + resortFees + taxes

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("book_room_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 110.dp)
        ) {
            // Top App Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("book_room_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = NavyPrimary
                        )
                    }

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "Reserve Your Stay",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = room.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted
                        )
                    }
                }
            }

            // 1. Interactive Calendar Date Range Picker
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("date_range_picker_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "October 2026",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontFamily = FontFamily.Serif,
                                    color = NavyPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Tap to select check-in and check-out",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SlateMuted
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = GoldContainer
                            ) {
                                Text(
                                    text = "$totalNights Nights",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = GoldDark,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Day of week headers
                        val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
                        Row(modifier = Modifier.fillMaxWidth()) {
                            daysOfWeek.forEach { dow ->
                                Text(
                                    text = dow,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SlateMuted,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Calendar Grid for October (31 days, Oct 1 was Thursday = 4 empty spots)
                        val totalSlots = 35
                        val weeks = totalSlots / 7
                        var dayCounter = 1

                        for (w in 0 until weeks) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                            ) {
                                for (d in 0..6) {
                                    val slotIndex = w * 7 + d
                                    if (slotIndex < 4 || dayCounter > 31) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    } else {
                                        val day = dayCounter
                                        val isSelectedStart = day == startDay
                                        val isSelectedEnd = day == endDay
                                        val isInRange = day in (startDay + 1)..(endDay - 1)
                                        val isToday = day == 20

                                        val bgColor = when {
                                            isSelectedStart || isSelectedEnd -> NavyPrimary
                                            isInRange -> GoldContainer
                                            else -> Color.Transparent
                                        }

                                        val textColor = when {
                                            isSelectedStart || isSelectedEnd -> Color.White
                                            isInRange -> GoldDark
                                            isToday -> GoldAccent
                                            else -> NavyPrimary
                                        }

                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(38.dp)
                                                .clip(
                                                    when {
                                                        isSelectedStart -> RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                                                        isSelectedEnd -> RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                                                        isInRange -> RoundedCornerShape(0.dp)
                                                        else -> RoundedCornerShape(8.dp)
                                                    }
                                                )
                                                .background(bgColor)
                                                .clickable {
                                                    if (day < startDay || (day > startDay && dayCounter <= endDay && day != endDay)) {
                                                        startDay = day
                                                        if (endDay <= startDay) endDay = (startDay + 2).coerceAtMost(31)
                                                    } else {
                                                        endDay = day
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "$day",
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = if (isSelectedStart || isSelectedEnd || isToday) FontWeight.Bold else FontWeight.Normal,
                                                color = textColor
                                            )
                                        }
                                        dayCounter++
                                    }
                                }
                            }
                        }

                        // Selected range summary
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(IvoryMuted)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Check-In", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                Text("Oct $startDay, 2026", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = NavyPrimary)
                                Text("from 3:00 PM", style = MaterialTheme.typography.labelSmall, color = SlateMuted, fontSize = 10.sp)
                            }
                            Box(modifier = Modifier.height(30.dp).width(1.dp).background(SlateBorder))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Check-Out", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                                Text("Oct $endDay, 2026", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = NavyPrimary)
                                Text("until 12:00 PM", style = MaterialTheme.typography.labelSmall, color = SlateMuted, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            // 2. Number of Guests Stepper
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("guests_stepper_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Number of Guests",
                                style = MaterialTheme.typography.titleMedium,
                                color = NavyPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Max capacity: ${room.capacity} guests",
                                style = MaterialTheme.typography.bodySmall,
                                color = SlateMuted
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedButton(
                                onClick = { if (guestsCount > 1) guestsCount-- },
                                shape = CircleShape,
                                modifier = Modifier.size(42.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = "$guestsCount",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )

                            OutlinedButton(
                                onClick = { if (guestsCount < room.capacity) guestsCount++ },
                                shape = CircleShape,
                                modifier = Modifier.size(42.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // 3. Price Breakdown (nights × rate + taxes)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("price_breakdown_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Price Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        PriceRow(
                            label = "$${room.pricePerNight.toInt()} × $totalNights nights",
                            amount = String.format("$%.2f", roomRate)
                        )

                        PriceRow(
                            label = "Resort & Hospitality Fee ($45/night)",
                            amount = String.format("$%.2f", resortFees)
                        )

                        PriceRow(
                            label = "State & Tourism Taxes (12%)",
                            amount = String.format("$%.2f", taxes)
                        )

                        Divider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = SlateBorder
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Total (USD)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary
                                )
                                Text(
                                    text = "Includes all luxury fees & taxes",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SlateMuted
                                )
                            }

                            Text(
                                text = String.format("$%.2f", grandTotal),
                                style = MaterialTheme.typography.headlineMedium,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                        }
                    }
                }
            }
        }

        // Bottom Fixed "Continue" CTA Bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding(),
            color = IvoryCard,
            shadowElevation = 12.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Grand Total",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted
                    )
                    Text(
                        text = String.format("$%.2f", grandTotal),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }

                Button(
                    onClick = {
                        viewModel.updateBookingDates(
                            checkIn = "Oct $startDay, 2026",
                            checkOut = "Oct $endDay, 2026",
                            nights = totalNights,
                            guests = guestsCount
                        )
                        viewModel.proceedToCheckout()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldAccent,
                        contentColor = NavyDark
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("continue_to_checkout_btn")
                ) {
                    Text(
                        text = "Continue to Checkout",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = SlateMuted)
        Text(text = amount, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
    }
}
