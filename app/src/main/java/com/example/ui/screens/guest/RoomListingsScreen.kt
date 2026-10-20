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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RoomType
import com.example.ui.StayEaseViewModel
import com.example.ui.components.RoomCard
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.IvoryBackground
import com.example.ui.theme.IvoryCard
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateMuted

@Composable
fun RoomListingsScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    val rooms by viewModel.filteredRooms.collectAsState()
    val selectedType by viewModel.selectedTypeFilter.collectAsState()
    val maxPrice by viewModel.maxPriceFilter.collectAsState()
    val minCapacity by viewModel.minCapacityFilter.collectAsState()

    var showFiltersDetail by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("room_listings_screen")
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Suites & Accommodations",
                        style = MaterialTheme.typography.displayMedium,
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Showing ${rooms.size} available luxury rooms",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (showFiltersDetail) GoldContainer else IvoryCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (showFiltersDetail) GoldAccent else SlateBorder),
                    modifier = Modifier.clickable { showFiltersDetail = !showFiltersDetail }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filters",
                            tint = if (showFiltersDetail) GoldDark else NavyPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (showFiltersDetail) "Hide Filters" else "Filters",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (showFiltersDetail) GoldDark else NavyPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

            // Room Type Filter Bar (Horizontal Chips)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(RoomType.values()) { type ->
                    val isSelected = selectedType == type
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setTypeFilter(type) },
                        label = {
                            Text(
                                text = type.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NavyPrimary,
                            selectedLabelColor = androidx.compose.ui.graphics.Color.White,
                            containerColor = IvoryCard,
                            labelColor = NavyPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) NavyPrimary else SlateBorder,
                            selectedBorderColor = NavyPrimary,
                            enabled = true,
                            selected = isSelected
                        ),
                        modifier = Modifier.testTag("filter_chip_${type.name}")
                    )
                }
            }

            // Expandable Filter Detail Section (Price & Capacity)
            if (showFiltersDetail) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = IvoryCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Price Slider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Max Price per Night",
                                style = MaterialTheme.typography.labelMedium,
                                color = NavyPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${maxPrice.toInt()}",
                                style = MaterialTheme.typography.labelLarge,
                                color = GoldDark,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Slider(
                            value = maxPrice.toFloat(),
                            onValueChange = { viewModel.setMaxPriceFilter(it.toDouble()) },
                            valueRange = 250f..1000f,
                            steps = 15,
                            colors = SliderDefaults.colors(
                                thumbColor = GoldAccent,
                                activeTrackColor = NavyPrimary,
                                inactiveTrackColor = IvoryBackground
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Capacity Buttons
                        Text(
                            text = "Minimum Capacity",
                            style = MaterialTheme.typography.labelMedium,
                            color = NavyPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(1, 2, 3, 4).forEach { cap ->
                                val isCapSelected = minCapacity == cap
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isCapSelected) NavyPrimary else IvoryBackground,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isCapSelected) NavyPrimary else SlateBorder),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { viewModel.setMinCapacityFilter(cap) }
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$cap+ Guests",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = if (isCapSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isCapSelected) androidx.compose.ui.graphics.Color.White else NavyPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Room Cards List
        if (rooms.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = SlateMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No Suites Match Your Filters",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = "Try adjusting your price ceiling or room type to view our world-class accommodations.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(rooms, key = { it.id }) { room ->
                    RoomCard(
                        room = room,
                        onClick = { viewModel.openRoomDetail(room.id) }
                    )
                }
            }
        }
    }
}
