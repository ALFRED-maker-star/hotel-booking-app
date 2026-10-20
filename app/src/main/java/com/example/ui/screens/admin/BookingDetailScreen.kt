package com.example.ui.screens.admin

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BookingStatus
import com.example.ui.StayEaseViewModel
import com.example.ui.components.AppDestination
import com.example.ui.theme.ErrorRed
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
fun BookingDetailScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AppDestination.ADMIN_CALENDAR)
    }

    val context = LocalContext.current
    val booking = viewModel.getSelectedBooking() ?: return
    var showCancelDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("admin_booking_detail_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // Top Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppDestination.ADMIN_CALENDAR) },
                        modifier = Modifier.testTag("admin_booking_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = NavyPrimary
                        )
                    }

                    Column(modifier = Modifier.padding(start = 6.dp)) {
                        Text(
                            text = "Guest Folio & Booking Detail",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Reference: ${booking.bookingRef}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 1. Current Reservation Status Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (booking.status) {
                            BookingStatus.CHECKED_IN -> SuccessGreenLight
                            BookingStatus.CONFIRMED -> GoldContainer
                            BookingStatus.COMPLETED -> IvoryMuted
                            BookingStatus.CANCELLED -> IvoryMuted
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "STAY STATUS",
                                style = MaterialTheme.typography.labelSmall,
                                color = SlateMuted
                            )
                            Text(
                                text = booking.status.label.uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = when (booking.status) {
                                    BookingStatus.CHECKED_IN -> SuccessGreen
                                    BookingStatus.CONFIRMED -> GoldDark
                                    else -> NavyPrimary
                                }
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = NavyPrimary
                        ) {
                            Text(
                                text = "ROOM ${booking.roomNumber}",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // 2. Guest Information Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("admin_guest_info_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Guest Profile & Contact",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        InfoRow(icon = Icons.Default.People, label = "Guest Name", value = booking.guestName)
                        InfoRow(icon = Icons.Default.Email, label = "Email", value = booking.guestEmail)
                        InfoRow(icon = Icons.Default.Phone, label = "Phone", value = booking.guestPhone)
                        InfoRow(icon = Icons.Default.People, label = "Party Size", value = "${booking.guestsCount} Guests")
                        InfoRow(icon = Icons.Default.Notes, label = "Special Requests", value = booking.specialRequests.ifBlank { "None recorded" })
                    }
                }
            }

            // 3. Room & Stay Dates Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("admin_dates_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Stay & Itinerary Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        InfoRow(icon = Icons.Default.MeetingRoom, label = "Accommodations", value = "${booking.roomName} (${booking.roomType.label})")
                        InfoRow(icon = Icons.Default.CalendarMonth, label = "Check-In Date", value = "${booking.checkInDate} (From 3:00 PM)")
                        InfoRow(icon = Icons.Default.CalendarMonth, label = "Check-Out Date", value = "${booking.checkOutDate} (Until 12:00 PM)")
                        InfoRow(icon = Icons.Default.CalendarMonth, label = "Total Duration", value = "${booking.totalNights} Nights")
                    }
                }
            }

            // 4. Payment Folio Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("admin_payment_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Payment & Billing Folio",
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = FontFamily.Serif,
                                color = NavyPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SuccessGreenLight
                            ) {
                                Text(
                                    text = booking.paymentStatus.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SuccessGreen,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        InfoRow(icon = Icons.Default.CreditCard, label = "Payment Method", value = booking.paymentMethod)
                        InfoRow(icon = Icons.Default.Shield, label = "Room Rate Total", value = String.format("$%.2f", booking.pricePerNight * booking.totalNights))
                        InfoRow(icon = Icons.Default.Shield, label = "Taxes & Resort Fees", value = String.format("$%.2f", booking.resortFees + booking.taxes))

                        Divider(modifier = Modifier.padding(vertical = 10.dp), color = SlateBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Paid Folio", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = NavyPrimary)
                            Text(String.format("$%.2f", booking.totalPrice), style = MaterialTheme.typography.titleMedium, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, color = GoldDark)
                        }
                    }
                }
            }
        }

        // 5. Fixed Check-In / Check-Out Actions Bottom Bar
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
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (booking.status) {
                    BookingStatus.CONFIRMED -> {
                        Button(
                            onClick = {
                                viewModel.updateBookingStatus(booking.id, BookingStatus.CHECKED_IN)
                                Toast.makeText(context, "Guest checked in! Key card issued for Room ${booking.roomNumber}.", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            modifier = Modifier.weight(1f).height(48.dp).testTag("action_check_in_btn")
                        ) {
                            Icon(Icons.Default.Key, contentDescription = null, tint = Color.White)
                            Text(
                                text = "Check-In Guest",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }

                        OutlinedButton(
                            onClick = { showCancelDialog = true },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(0.9f).height(48.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
                        ) {
                            Text("Cancel Stay", color = ErrorRed, fontWeight = FontWeight.Bold)
                        }
                    }
                    BookingStatus.CHECKED_IN -> {
                        Button(
                            onClick = {
                                viewModel.updateBookingStatus(booking.id, BookingStatus.COMPLETED)
                                Toast.makeText(context, "Guest checked out. Housekeeping alerted for Room ${booking.roomNumber}.", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("action_check_out_btn")
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = GoldAccent)
                            Text(
                                text = "Check-Out Guest & Release Room",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    else -> {
                        Button(
                            onClick = {
                                viewModel.updateBookingStatus(booking.id, BookingStatus.CONFIRMED)
                                Toast.makeText(context, "Reservation reinstated as Confirmed.", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Reactivate Reservation", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = {
                Text("Cancel Reservation", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, color = NavyPrimary)
            },
            text = {
                Text("Are you sure you want to cancel reservation ${booking.bookingRef}? The room will be immediately returned to available inventory.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cancelBooking(booking.id)
                        showCancelDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed)
                ) {
                    Text("Confirm Cancel", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Back", color = NavyPrimary)
                }
            }
        )
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoldDark,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = SlateMuted,
            modifier = Modifier.padding(start = 8.dp).width(120.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = NavyPrimary,
            modifier = Modifier.weight(1f)
        )
    }
}
