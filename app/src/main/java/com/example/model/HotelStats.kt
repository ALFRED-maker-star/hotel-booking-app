package com.example.model

data class DayOccupancy(
    val dayNumber: Int,
    val dayOfWeek: String,
    val occupancyPercentage: Int, // 0 - 100
    val bookedCount: Int,
    val totalCount: Int = 24
)

data class HotelStats(
    val occupancyRate: Int = 84,
    val occupancyDelta: String = "+6% vs last week",
    val bookingsToday: Int = 14,
    val bookingsTodayDelta: String = "+3 from yesterday",
    val revenueThisMonth: Double = 148250.0,
    val revenueDelta: String = "+18.4% MoM",
    val availableRooms: Int = 8,
    val totalRooms: Int = 32,
    val heatmapDays: List<DayOccupancy>
)
