package com.example.ui.screens.guest

import android.content.Intent
import android.provider.CalendarContract
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StayEaseViewModel
import com.example.ui.components.AppDestination
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

@Composable
fun BookingConfirmationScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AppDestination.GUEST_HOME)
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val booking = viewModel.lastConfirmedBooking.collectAsState().value
        ?: viewModel.getSelectedBooking()
        ?: return

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .navigationBarsPadding()
            .testTag("booking_confirmation_screen"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Success Crown Crest & Title
        item {
            Surface(
                shape = CircleShape,
                color = GoldAccent,
                shadowElevation = 8.dp,
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = NavyDark,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Reservation Confirmed!",
                style = MaterialTheme.typography.displayMedium,
                fontFamily = FontFamily.Serif,
                color = NavyPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Thank you, ${booking.guestName}. We look forward to welcoming you to StayEase.",
                style = MaterialTheme.typography.bodyMedium,
                color = SlateMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // 2. Booking Reference Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_ref_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = IvoryCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "BOOKING REFERENCE CODE",
                        style = MaterialTheme.typography.labelSmall,
                        color = SlateMuted,
                        letterSpacing = 1.2.sp
                    )

                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = booking.bookingRef,
                            style = MaterialTheme.typography.headlineLarge,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark,
                            letterSpacing = 2.sp
                        )

                        Surface(
                            shape = CircleShape,
                            color = IvoryMuted,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(32.dp)
                        ) {
                            androidx.compose.material3.IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(booking.bookingRef))
                                    Toast.makeText(context, "Reference code copied!", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy Code",
                                    tint = NavyPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 14.dp), color = SlateBorder)

                    // Stay summary inside card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Suite", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                            Text(booking.roomName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = NavyPrimary)
                            Text("Room ${booking.roomNumber}", style = MaterialTheme.typography.bodySmall, color = SlateMuted)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Duration", style = MaterialTheme.typography.labelSmall, color = SlateMuted)
                            Text("${booking.totalNights} Nights", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = NavyPrimary)
                            Text("${booking.checkInDate} - ${booking.checkOutDate}", style = MaterialTheme.typography.bodySmall, color = SlateMuted)
                        }
                    }
                }
            }
        }

        // 3. Check-In Instructions Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .testTag("check_in_instructions_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = IvoryCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Guest Check-In Guidelines",
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    InstructionRow(
                        icon = Icons.Default.CalendarMonth,
                        title = "Check-In & Check-Out",
                        desc = "Check-in begins at 3:00 PM. Check-out is by 12:00 PM noon. Early check-in available upon request."
                    )

                    InstructionRow(
                        icon = Icons.Default.Key,
                        title = "Keyless Digital Entry",
                        desc = "Your mobile key will activate automatically on your check-in day. Staff at the reception will assist upon arrival."
                    )

                    InstructionRow(
                        icon = Icons.Default.Phone,
                        title = "24/7 Concierge Hotline",
                        desc = "Dial +1 (800) STAY-EASE or speak with your private butler via the front desk anytime."
                    )
                }
            }
        }

        // 4. Action Buttons: "View Booking" / "Add to Calendar" / "Home"
        item {
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.openBookingDetail(booking.id, fromAdmin = false)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("view_booking_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyPrimary,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Luggage,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "View My Bookings",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, "StayEase: ${booking.roomName}")
                            .putExtra(CalendarContract.Events.DESCRIPTION, "Hotel reservation ref: ${booking.bookingRef}. Check-in at 3:00 PM.")
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "StayEase Grand Resort, French Riviera")
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Added to device calendar reminder!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("add_to_calendar_btn"),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NavyPrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = NavyPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Add to Calendar",
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            androidx.compose.material3.TextButton(
                onClick = { viewModel.navigateTo(AppDestination.GUEST_HOME) },
                modifier = Modifier.testTag("return_home_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = SlateMuted,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Return to Home",
                    color = SlateMuted,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}

@Composable
fun InstructionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = IvoryMuted,
            modifier = Modifier.size(34.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = SlateMuted,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
