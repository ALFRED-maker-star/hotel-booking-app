package com.example.model

enum class BookingStatus(val label: String) {
    CONFIRMED("Confirmed"),
    CHECKED_IN("Checked In"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

data class Booking(
    val id: String,
    val bookingRef: String,
    val roomId: String,
    val roomName: String,
    val roomNumber: String,
    val roomType: RoomType,
    val guestName: String,
    val guestEmail: String,
    val guestPhone: String,
    val checkInDate: String,
    val checkOutDate: String,
    val checkInDayIndex: Int = 24, // day offset for calendar visual
    val stayDurationDays: Int = 3,
    val guestsCount: Int,
    val totalNights: Int,
    val pricePerNight: Double,
    val resortFees: Double,
    val taxes: Double,
    val totalPrice: Double,
    val specialRequests: String = "",
    val paymentMethod: String = "Visa •••• 8842",
    val paymentStatus: String = "Paid",
    val status: BookingStatus = BookingStatus.CONFIRMED,
    val createdAt: String = "Oct 20, 2026"
)
