package com.example.ui.screens.guest

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Room
import com.example.ui.StayEaseViewModel
import com.example.ui.components.AmenityIconChip
import com.example.ui.components.AppDestination
import com.example.ui.components.HotelHeroVisual
import com.example.ui.components.RoomCard
import com.example.ui.components.getAmenityIcon
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
fun HomeScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    val rooms by viewModel.rooms.collectAsState()
    val searchCheckIn by viewModel.searchCheckIn.collectAsState()
    val searchCheckOut by viewModel.searchCheckOut.collectAsState()
    val searchGuests by viewModel.searchGuests.collectAsState()

    var showSearchDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // 1. Hero Image of Hotel with Welcome Banner
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                HotelHeroVisual(height = 280.dp)

                // Overlay Text on Hero
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = NavyDark.copy(alpha = 0.75f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "French Riviera • Monte Carlo",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = "Experience Sublime\nSanctuary & Elegance",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                    )

                    Text(
                        text = "Five-star seaside suites, private plunge villas & Michelin dining",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE2E8F0),
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }

        // 2. Search Bar Card (Check-in/Check-out dates & guests)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
                    .testTag("home_search_bar_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = IvoryCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Plan Your Stay",
                        style = MaterialTheme.typography.titleMedium,
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dates and guests row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(IvoryMuted)
                            .clickable { showSearchDialog = true }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Dates",
                            tint = GoldAccent,
                            modifier = Modifier.size(22.dp)
                        )
                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text(
                                text = "Stay Dates",
                                style = MaterialTheme.typography.labelSmall,
                                color = SlateMuted
                            )
                            Text(
                                text = "$searchCheckIn – $searchCheckOut",
                                style = MaterialTheme.typography.bodyMedium,
                                color = NavyPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(28.dp)
                                .width(1.dp)
                                .background(SlateBorder)
                                .padding(horizontal = 4.dp)
                        )

                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "Guests",
                            tint = GoldAccent,
                            modifier = Modifier.size(20.dp).padding(start = 8.dp)
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(
                                text = "Guests",
                                style = MaterialTheme.typography.labelSmall,
                                color = SlateMuted
                            )
                            Text(
                                text = "$searchGuests Guests",
                                style = MaterialTheme.typography.bodyMedium,
                                color = NavyPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.navigateTo(AppDestination.GUEST_ROOMS) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("explore_rooms_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyPrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Check Available Rooms",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        // 3. Amenities Icon Row
        item {
            Column(modifier = Modifier.padding(top = 24.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Curated Hotel Amenities",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val hotelAmenities = listOf(
                    "Infinity Pool" to getAmenityIcon("pool"),
                    "Michelin Dining" to getAmenityIcon("dining"),
                    "Luxury Spa" to getAmenityIcon("spa"),
                    "Private Beach" to getAmenityIcon("water"),
                    "Cocktail Bar" to getAmenityIcon("bar"),
                    "24/7 Butler" to getAmenityIcon("butler"),
                    "Fitness Studio" to getAmenityIcon("gym"),
                    "Wi-Fi 6 Ultra" to getAmenityIcon("wifi")
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(hotelAmenities) { (name, icon) ->
                        AmenityIconChip(title = name, icon = icon)
                    }
                }
            }
        }

        // 4. Featured Rooms Carousel
        item {
            Column(modifier = Modifier.padding(top = 28.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Featured Suites & Villas",
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = FontFamily.Serif,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Handcrafted spaces with panoramic views",
                            style = MaterialTheme.typography.bodySmall,
                            color = SlateMuted
                        )
                    }

                    TextButton(
                        onClick = { viewModel.navigateTo(AppDestination.GUEST_ROOMS) },
                        modifier = Modifier.testTag("view_all_rooms_btn")
                    ) {
                        Text(
                            text = "See All",
                            color = GoldDark,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = GoldDark,
                            modifier = Modifier.size(16.dp).padding(start = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.testTag("featured_rooms_carousel")
                ) {
                    items(rooms.take(4)) { room ->
                        Box(modifier = Modifier.width(310.dp)) {
                            RoomCard(
                                room = room,
                                onClick = { viewModel.openRoomDetail(room.id) }
                            )
                        }
                    }
                }
            }
        }

        // 5. Special Hospitality Welcome Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 28.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDark)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = GoldAccent,
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = NavyDark,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                        Text(
                            text = "StayEase Concierge Guarantee",
                            style = MaterialTheme.typography.titleMedium,
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Free cancellation up to 48 hours before check-in. Complimentary luxury airport chauffeur included with all Penthouse and Villa stays.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }

    // Interactive Search Dialog for modifying dates & guests
    if (showSearchDialog) {
        SearchCriteriaDialog(
            initialCheckIn = searchCheckIn,
            initialCheckOut = searchCheckOut,
            initialGuests = searchGuests,
            onDismiss = { showSearchDialog = false },
            onSave = { checkIn, checkOut, guests ->
                viewModel.updateSearchCriteria(checkIn, checkOut, guests)
                showSearchDialog = false
            }
        )
    }
}

@Composable
fun SearchCriteriaDialog(
    initialCheckIn: String,
    initialCheckOut: String,
    initialGuests: Int,
    onDismiss: () -> Unit,
    onSave: (String, String, Int) -> Unit
) {
    var checkIn by remember { mutableStateOf(initialCheckIn) }
    var checkOut by remember { mutableStateOf(initialCheckOut) }
    var guests by remember { mutableIntStateOf(initialGuests) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Stay Dates & Guests",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                color = NavyPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Preset Itineraries:",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Date Presets
                val presets = listOf(
                    Triple("Oct 24, 2026", "Oct 27, 2026", "Upcoming Weekend (3 N)"),
                    Triple("Nov 05, 2026", "Nov 09, 2026", "Autumn Escape (4 N)"),
                    Triple("Dec 24, 2026", "Dec 29, 2026", "Holiday Season (5 N)")
                )

                presets.forEach { (ci, co, label) ->
                    val isSelected = checkIn == ci && checkOut == co
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) GoldContainer else IvoryMuted,
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, GoldAccent) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                checkIn = ci
                                checkOut = co
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) GoldDark else NavyPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$ci – $co",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SlateMuted
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Number of Guests:",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$guests Adult Guests",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = NavyPrimary
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = { if (guests > 1) guests-- },
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Text(
                            text = "$guests",
                            modifier = Modifier.padding(horizontal = 14.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )

                        OutlinedButton(
                            onClick = { if (guests < 6) guests++ },
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(checkIn, checkOut, guests) },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
                Text("Apply Selection", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = SlateMuted)
            }
        }
    )
}
