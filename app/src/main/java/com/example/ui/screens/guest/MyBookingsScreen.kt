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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.ui.StayEaseViewModel
import com.example.ui.components.AppDestination
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedLight
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
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
import com.example.ui.theme.WarningAmberLight

@Composable
fun MyBookingsScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AppDestination.GUEST_HOME)
    }

    val bookings by viewModel.bookings.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Upcoming, 1: Past
    var bookingToCancel by remember { mutableStateOf<Booking?>(null) }
    var bookingToModify by remember { mutableStateOf<Booking?>(null) }

    val upcomingStays = bookings.filter {
        it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.CHECKED_IN
    }
    val pastStays = bookings.filter {
        it.status == BookingStatus.COMPLETED || it.status == BookingStatus.CANCELLED
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("my_bookings_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "My Reservations",
                style = MaterialTheme.typography.displayMedium,
                fontFamily = FontFamily.Serif,
                color = NavyPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Manage your itineraries, room keys, and concierge services",
                style = MaterialTheme.typography.bodySmall,
                color = SlateMuted
            )
        }

        // Tabs: Upcoming Stays & Past Stays
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = IvoryBackground,
            contentColor = NavyPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = GoldAccent
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "Upcoming (${upcomingStays.size})",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) NavyPrimary else SlateMuted
                    )
                },
                modifier = Modifier.testTag("tab_upcoming_stays")
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "Past & Cancelled (${pastStays.size})",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) NavyPrimary else SlateMuted
                    )
                },
                modifier = Modifier.testTag("tab_past_stays")
            )
        }

        val displayList = if (selectedTab == 0) upcomingStays else pastStays

        if (displayList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Luggage,
                        contentDescription = null,
                        tint = SlateMuted,
                        modifier = Modifier.size(54.dp)
                    )
                    Text(
                        text = if (selectedTab == 0) "No Upcoming Reservations" else "No Past Stays",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = "Discover luxury suites and plan your next memorable retreat.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.navigateTo(AppDestination.GUEST_ROOMS) },
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                    ) {
                        Text("Browse Available Rooms", color = Color.White)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(displayList, key = { it.id }) { booking ->
                    GuestBookingCard(
                        booking = booking,
                        onCancelClick = { bookingToCancel = booking },
                        onModifyClick = { bookingToModify = booking }
                    )
                }
            }
        }
    }

    // Cancellation Confirmation Dialog
    bookingToCancel?.let { b ->
        AlertDialog(
            onDismissRequest = { bookingToCancel = null },
            title = {
                Text(
                    text = "Cancel Reservation?",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Are you sure you want to cancel reservation ${b.bookingRef} for ${b.roomName}?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NavyPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Per StayEase policy, a full refund of $${b.totalPrice.toInt()} will be credited back to ${b.paymentMethod}.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cancelBooking(b.id)
                        bookingToCancel = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                ) {
                    Text("Confirm Cancellation", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { bookingToCancel = null }) {
                    Text("Keep Reservation", color = NavyPrimary)
                }
            }
        )
    }

    // Modify Dates Dialog
    bookingToModify?.let { b ->
        ModifyDatesDialog(
            booking = b,
            onDismiss = { bookingToModify = null },
            onSave = { newCheckIn, newCheckOut ->
                // In demo repository, update date
                bookingToModify = null
            }
        )
    }
}

@Composable
fun GuestBookingCard(
    booking: Booking,
    onCancelClick: () -> Unit,
    onModifyClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_card_${booking.bookingRef}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IvoryCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Status & Ref Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booking.bookingRef,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = GoldDark,
                    letterSpacing = 1.sp
                )

                val (badgeBg, badgeFg, label) = when (booking.status) {
                    BookingStatus.CONFIRMED -> Triple(GoldContainer, GoldDark, "Confirmed")
                    BookingStatus.CHECKED_IN -> Triple(SuccessGreenLight, SuccessGreen, "Active Stay")
                    BookingStatus.COMPLETED -> Triple(IvoryMuted, SlateMuted, "Completed")
                    BookingStatus.CANCELLED -> Triple(ErrorRedLight, ErrorRed, "Cancelled")
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = badgeBg
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = badgeFg,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Room Name & Details
            Text(
                text = booking.roomName,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )

            Text(
                text = "Room ${booking.roomNumber} • ${booking.roomType.label} • ${booking.guestsCount} Guests",
                style = MaterialTheme.typography.bodySmall,
                color = SlateMuted
            )

            // Stay Dates & Amount Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(IvoryMuted, RoundedCornerShape(10.dp))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(18.dp)
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "${booking.checkInDate} – ${booking.checkOutDate}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Text(
                            text = "${booking.totalNights} Nights Duration",
                            style = MaterialTheme.typography.labelSmall,
                            color = SlateMuted
                        )
                    }
                }

                Text(
                    text = String.format("$%.2f", booking.totalPrice),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
            }

            // Actions for active upcoming stays
            if (booking.status == BookingStatus.CONFIRMED || booking.status == BookingStatus.CHECKED_IN) {
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = SlateBorder)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onModifyClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = null,
                            tint = NavyPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Modify Dates",
                            color = NavyPrimary,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    OutlinedButton(
                        onClick = onCancelClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ErrorRed
                        ),
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null,
                            tint = ErrorRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Cancel Stay",
                            color = ErrorRed,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModifyDatesDialog(
    booking: Booking,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var checkIn by remember { mutableStateOf(booking.checkInDate) }
    var checkOut by remember { mutableStateOf(booking.checkOutDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Modify Stay Dates",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Select a new check-in and check-out window for ${booking.roomName}:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateMuted
                )
                Spacer(modifier = Modifier.height(12.dp))

                val options = listOf(
                    "Nov 02, 2026 – Nov 05, 2026",
                    "Nov 12, 2026 – Nov 15, 2026",
                    "Dec 01, 2026 – Dec 04, 2026"
                )

                options.forEach { opt ->
                    val isSelected = checkIn.contains("Nov") || checkIn.contains("Dec")
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = IvoryMuted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                val parts = opt.split(" – ")
                                checkIn = parts[0]
                                checkOut = parts[1]
                            }
                    ) {
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = NavyPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(checkIn, checkOut) },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
                Text("Confirm Change", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Keep Current", color = SlateMuted)
            }
        }
    )
}
