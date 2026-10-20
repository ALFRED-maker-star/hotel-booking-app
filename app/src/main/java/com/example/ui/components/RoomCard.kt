package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Room
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.IvoryCard
import com.example.ui.theme.IvoryMuted
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateMuted
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomCard(
    room: Room,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    photoHeight: Dp = 190.dp
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("room_card_${room.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = IvoryCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Photo with availability badge & rating
            val badge = when {
                room.isMaintenance -> "Under Maintenance"
                room.id == "room-1" -> "Signature Suite"
                room.id == "room-2" -> "Private Pool"
                else -> "Available"
            }

            RoomPhotoVisual(
                artTheme = room.artTheme,
                height = photoHeight,
                badgeText = badge,
                rating = room.rating
            )

            // Content details
            Column(modifier = Modifier.padding(16.dp)) {
                // Room Type & Room Number
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = GoldContainer
                    ) {
                        Text(
                            text = room.type.label.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldDark,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "Room ${room.roomNumber}",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted
                    )
                }

                // Name
                Text(
                    text = room.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = NavyPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 6.dp)
                )

                // Key Specs (Capacity, Bed, Size)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "Guests",
                            tint = SlateMuted,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "${room.capacity} Guests",
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bed,
                            contentDescription = "Bed",
                            tint = SlateMuted,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = room.bedType,
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SquareFoot,
                            contentDescription = "Size",
                            tint = SlateMuted,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "${room.sizeSqFt} sq ft",
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                // Highlights FlowRow
                FlowRow(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    room.highlights.take(3).forEach { hl ->
                        AmenitySmallTag(text = hl)
                    }
                }

                // Price and action row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$${room.pricePerNight.toInt()}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = NavyPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = " / night",
                                style = MaterialTheme.typography.bodySmall,
                                color = SlateMuted,
                                modifier = Modifier.padding(bottom = 3.dp, start = 2.dp)
                            )
                        }
                        Text(
                            text = "Excl. taxes & fees",
                            style = MaterialTheme.typography.labelSmall,
                            color = SlateMuted,
                            fontSize = 10.sp
                        )
                    }

                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (room.isMaintenance) SlateMuted else NavyPrimary,
                            contentColor = androidx.compose.ui.graphics.Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("view_room_btn_${room.id}")
                    ) {
                        Text(
                            text = if (room.isMaintenance) "Maintenance" else "View Details",
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp).padding(start = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
