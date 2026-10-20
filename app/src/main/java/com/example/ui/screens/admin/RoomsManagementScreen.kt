package com.example.ui.screens.admin

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MeetingRoom
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.example.model.Room
import com.example.model.RoomType
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
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight

@Composable
fun RoomsManagementScreen(
    viewModel: StayEaseViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AppDestination.ADMIN_DASHBOARD)
    }

    val rooms by viewModel.rooms.collectAsState()
    var showAddRoomDialog by remember { mutableStateOf(false) }
    var editingRoom by remember { mutableStateOf<Room?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .testTag("rooms_management_screen")
    ) {
        // Top Bar
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
                    modifier = Modifier.testTag("rooms_management_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NavyPrimary
                    )
                }
                Column(modifier = Modifier.padding(start = 6.dp)) {
                    Text(
                        text = "Rooms Inventory Table",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${rooms.size} suites & villas configured",
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateMuted
                    )
                }
            }

            Button(
                onClick = { showAddRoomDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldAccent,
                    contentColor = NavyDark
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                modifier = Modifier.testTag("add_room_btn")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Text(
                    text = "Add Room",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        // Table Header
        Surface(
            color = NavyPrimary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ROOM / TYPE",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.8f)
                )
                Text(
                    text = "RATE",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "STATUS",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    text = "ACTIONS",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.1f)
                )
            }
        }

        // Rooms Table Rows
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(rooms, key = { it.id }) { room ->
                RoomTableRow(
                    room = room,
                    onToggleMaintenance = { viewModel.toggleRoomMaintenance(room.id) },
                    onEditClick = { editingRoom = room }
                )
                Divider(color = SlateBorder)
            }
        }
    }

    // Add Room Dialog
    if (showAddRoomDialog) {
        RoomFormDialog(
            initialRoom = null,
            onDismiss = { showAddRoomDialog = false },
            onSave = { newRoom ->
                viewModel.addNewRoom(newRoom)
                showAddRoomDialog = false
            }
        )
    }

    // Edit Room Dialog
    editingRoom?.let { room ->
        RoomFormDialog(
            initialRoom = room,
            onDismiss = { editingRoom = null },
            onSave = { updatedRoom ->
                viewModel.updateRoom(updatedRoom)
                editingRoom = null
            }
        )
    }
}

@Composable
fun RoomTableRow(
    room: Room,
    onToggleMaintenance: () -> Unit,
    onEditClick: () -> Unit
) {
    Surface(
        color = IvoryCard,
        modifier = Modifier.fillMaxWidth().testTag("room_table_row_${room.roomNumber}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Room & Type
            Column(modifier = Modifier.weight(1.8f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Room ${room.roomNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }
                Text(
                    text = room.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateMuted,
                    maxLines = 1
                )
                Text(
                    text = "${room.type.label} • Floor ${room.floor}",
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldDark,
                    fontSize = 10.sp
                )
            }

            // Price / Rate
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$${room.pricePerNight.toInt()}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "/ night",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateMuted,
                    fontSize = 10.sp
                )
            }

            // Status Badge
            Column(modifier = Modifier.weight(1.2f)) {
                val (bg, fg, statusLabel) = when {
                    room.isMaintenance -> Triple(WarningAmberLight, WarningAmber, "Maintenance")
                    room.isAvailable -> Triple(SuccessGreenLight, SuccessGreen, "Available")
                    else -> Triple(IvoryMuted, SlateMuted, "Occupied")
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = bg
                ) {
                    Text(
                        text = statusLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = fg,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp
                    )
                }
            }

            // Actions (Edit & Toggle Maintenance)
            Row(
                modifier = Modifier.weight(1.1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp).testTag("edit_room_${room.roomNumber}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Room",
                        tint = NavyPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = onToggleMaintenance,
                    modifier = Modifier.size(32.dp).testTag("maintenance_toggle_${room.roomNumber}")
                ) {
                    Icon(
                        imageVector = if (room.isMaintenance) Icons.Default.CheckCircle else Icons.Default.Build,
                        contentDescription = "Toggle Maintenance",
                        tint = if (room.isMaintenance) SuccessGreen else WarningAmber,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RoomFormDialog(
    initialRoom: Room?,
    onDismiss: () -> Unit,
    onSave: (Room) -> Unit
) {
    val isEdit = initialRoom != null
    var roomNumber by remember { mutableStateOf(initialRoom?.roomNumber ?: "602") }
    var name by remember { mutableStateOf(initialRoom?.name ?: "Royal Mediterranean Penthouse") }
    var priceText by remember { mutableStateOf(initialRoom?.pricePerNight?.toInt()?.toString() ?: "520") }
    var capacityText by remember { mutableStateOf(initialRoom?.capacity?.toString() ?: "3") }
    var floorText by remember { mutableStateOf(initialRoom?.floor?.toString() ?: "6") }
    var bedType by remember { mutableStateOf(initialRoom?.bedType ?: "1 King Bed + 1 Sofa") }
    var description by remember { mutableStateOf(initialRoom?.description ?: "Elegant panoramic luxury suite with ocean balcony.") }
    var isMaintenance by remember { mutableStateOf(initialRoom?.isMaintenance ?: false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isEdit) "Edit Room ${initialRoom?.roomNumber}" else "Add New Hotel Room",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = roomNumber,
                        onValueChange = { roomNumber = it },
                        label = { Text("Room #") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it },
                        label = { Text("Nightly Rate ($)") },
                        modifier = Modifier.weight(1.2f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Room Name / Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = capacityText,
                        onValueChange = { capacityText = it },
                        label = { Text("Capacity") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = floorText,
                        onValueChange = { floorText = it },
                        label = { Text("Floor") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = bedType,
                    onValueChange = { bedType = it },
                    label = { Text("Bed Configuration") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mark Under Maintenance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NavyPrimary
                    )
                    Switch(
                        checked = isMaintenance,
                        onCheckedChange = { isMaintenance = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GoldAccent,
                            checkedTrackColor = NavyPrimary
                        )
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull() ?: 450.0
                    val capacity = capacityText.toIntOrNull() ?: 2
                    val floor = floorText.toIntOrNull() ?: 3

                    val room = initialRoom?.copy(
                        roomNumber = roomNumber,
                        name = name,
                        pricePerNight = price,
                        capacity = capacity,
                        floor = floor,
                        bedType = bedType,
                        description = description,
                        isMaintenance = isMaintenance,
                        isAvailable = !isMaintenance
                    ) ?: Room(
                        id = "room-${System.currentTimeMillis()}",
                        roomNumber = roomNumber,
                        name = name,
                        type = RoomType.SUITE,
                        pricePerNight = price,
                        capacity = capacity,
                        rating = 4.9,
                        reviewCount = 1,
                        isAvailable = !isMaintenance,
                        isMaintenance = isMaintenance,
                        bedType = bedType,
                        sizeSqFt = 750,
                        floor = floor,
                        viewType = "Ocean & Riviera Panorama",
                        description = description,
                        amenities = listOf("High-Speed Wi-Fi", "Espresso Bar", "Rain Shower", "King Bed"),
                        highlights = listOf("Luxury View", "Modern Suite"),
                        artTheme = "presidential"
                    )
                    onSave(room)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
                Text(if (isEdit) "Save Changes" else "Add Room", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = SlateMuted)
            }
        }
    )
}
