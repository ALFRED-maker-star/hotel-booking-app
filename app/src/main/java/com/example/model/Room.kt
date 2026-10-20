package com.example.model

enum class RoomType(val label: String) {
    ALL("All Rooms"),
    PENTHOUSE("Penthouse"),
    SUITE("Suite"),
    VILLA("Villa"),
    EXECUTIVE("Executive"),
    DELUXE("Deluxe")
}

data class Room(
    val id: String,
    val roomNumber: String,
    val name: String,
    val type: RoomType,
    val pricePerNight: Double,
    val capacity: Int,
    val rating: Double,
    val reviewCount: Int,
    val isAvailable: Boolean = true,
    val isMaintenance: Boolean = false,
    val bedType: String,
    val sizeSqFt: Int,
    val floor: Int,
    val viewType: String,
    val description: String,
    val amenities: List<String>,
    val highlights: List<String>,
    val artTheme: String // "presidential", "ocean", "garden", "executive", "penthouse", "studio"
)
