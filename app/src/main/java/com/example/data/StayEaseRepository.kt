package com.example.data

import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.model.DayOccupancy
import com.example.model.HotelStats
import com.example.model.Room
import com.example.model.RoomType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class StayEaseRepository {

    private val initialRooms = listOf(
        Room(
            id = "room-1",
            roomNumber = "501",
            name = "Presidential Penthouse Suite",
            type = RoomType.PENTHOUSE,
            pricePerNight = 850.0,
            capacity = 4,
            rating = 4.98,
            reviewCount = 86,
            isAvailable = true,
            isMaintenance = false,
            bedType = "1 King Bed + 1 Queen Bed",
            sizeSqFt = 1680,
            floor = 5,
            viewType = "Panoramic Ocean & Skyline",
            description = "Experience supreme pinnacle luxury on the top floor. Featuring private elevator access, floor-to-ceiling panoramic glass, a wraparound sunset terrace, marble soaking bath, and dedicated 24-hour private butler service.",
            amenities = listOf(
                "Private Butler 24/7",
                "Wraparound Terrace",
                "Italian Marble Tub",
                "Dual Vanity",
                "Nespresso Reserve Bar",
                "Complimentary Champagne",
                "Ultra-fast Wi-Fi 6",
                "B&O Sound System"
            ),
            highlights = listOf("Top Floor View", "Private Butler", "1,680 sq ft", "Free Airport Limo"),
            artTheme = "presidential"
        ),
        Room(
            id = "room-2",
            roomNumber = "101",
            name = "Azure Ocean Villa & Pool",
            type = RoomType.VILLA,
            pricePerNight = 680.0,
            capacity = 4,
            rating = 4.96,
            reviewCount = 114,
            isAvailable = true,
            isMaintenance = false,
            bedType = "1 King Bed + 2 Twin Daybeds",
            sizeSqFt = 1420,
            floor = 1,
            viewType = "Direct Oceanfront & Private Pool",
            description = "A serene coastal sanctuary steps from warm white sands. Features a private heated plunge pool, teak wood sundeck with double loungers, an open-concept living pavilion, and secluded tropical landscaping.",
            amenities = listOf(
                "Private Heated Pool",
                "Direct Beach Access",
                "Outdoor Rain Shower",
                "Chef Kitchenette",
                "Evening Turndown Service",
                "Luxury Linens",
                "Smart Home Controls",
                "Breakfast in Bed"
            ),
            highlights = listOf("Private Plunge Pool", "Direct Beach Access", "Teak Sundeck", "Sunset Patio"),
            artTheme = "ocean"
        ),
        Room(
            id = "room-3",
            roomNumber = "304",
            name = "Grand Executive King Suite",
            type = RoomType.EXECUTIVE,
            pricePerNight = 420.0,
            capacity = 2,
            rating = 4.92,
            reviewCount = 142,
            isAvailable = true,
            isMaintenance = false,
            bedType = "1 Cal-King Bed",
            sizeSqFt = 780,
            floor = 3,
            viewType = "Yacht Harbor & Marina View",
            description = "Impeccably tailored for discerning travelers. Hand-stitched leather furnishings, plush California King bed, executive workstation with high-speed fiber connectivity, and access to the private Executive Club Lounge.",
            amenities = listOf(
                "Club Lounge Access",
                "Harbor Balcony",
                "Ergonomic Workstation",
                "Deep Soaking Tub",
                "Dyson Hair Care",
                "Mini Bar Included",
                "Express Garment Pressing",
                "Morning Valet Coffee"
            ),
            highlights = listOf("Club Lounge Access", "High Floor Marina View", "Dyson Amenities", "King Bed"),
            artTheme = "executive"
        ),
        Room(
            id = "room-4",
            roomNumber = "108",
            name = "Royal Garden Sanctuary Villa",
            type = RoomType.VILLA,
            pricePerNight = 540.0,
            capacity = 3,
            rating = 4.89,
            reviewCount = 95,
            isAvailable = true,
            isMaintenance = false,
            bedType = "1 King Bed + 1 Daybed",
            sizeSqFt = 980,
            floor = 1,
            viewType = "Botanical Garden Courtyard",
            description = "Nestled amidst lush orchid gardens and trickling stone fountains. Features a secluded garden veranda with daybed, artisan stoneware bathroom, and serene natural ambiance away from city bustle.",
            amenities = listOf(
                "Private Botanical Patio",
                "Botanical Tea Bar",
                "Artisan Stone Bath",
                "Aromatherapy Menu",
                "Plush Spa Robes",
                "Daily Fresh Fruit",
                "Garden Hammock",
                "Soundproof Design"
            ),
            highlights = listOf("Private Garden", "Stone Soaking Tub", "Aromatherapy Bar", "Peaceful Haven"),
            artTheme = "garden"
        ),
        Room(
            id = "room-5",
            roomNumber = "402",
            name = "Riviera Terrace Suite",
            type = RoomType.SUITE,
            pricePerNight = 380.0,
            capacity = 2,
            rating = 4.88,
            reviewCount = 120,
            isAvailable = true,
            isMaintenance = false,
            bedType = "1 King Bed",
            sizeSqFt = 690,
            floor = 4,
            viewType = "Coastal Skyline & Hills",
            description = "Elegance with a romantic touch. Features French doors opening onto a private wrought-iron terrace, plush velvet seating, a marble fireplace, and ambient cove lighting.",
            amenities = listOf(
                "Private Sun Terrace",
                "Marble Fireplace",
                "Rain Shower & Tub",
                "Boutique Wine Bar",
                "Curated Art Collection",
                "Smart Streaming 65\"",
                "Luxury Toiletries",
                "Late Check-Out Option"
            ),
            highlights = listOf("Sunset Balcony", "Fireplace", "Romantic Vibe", "Late Check-Out"),
            artTheme = "presidential"
        ),
        Room(
            id = "room-6",
            roomNumber = "205",
            name = "Monaco Horizon Studio",
            type = RoomType.DELUXE,
            pricePerNight = 290.0,
            capacity = 2,
            rating = 4.85,
            reviewCount = 78,
            isAvailable = true,
            isMaintenance = false,
            bedType = "1 Queen Bed",
            sizeSqFt = 520,
            floor = 2,
            viewType = "City Boulevard & Plaza",
            description = "Modern boutique sophistication designed for comfort. Floor-to-ceiling soundproof glass overlooking the Grand Plaza, custom oak cabinetry, spa rain shower, and seamless wireless connectivity.",
            amenities = listOf(
                "Soundproof Triple Glass",
                "Spa Rain Shower",
                "Espresso Machine",
                "Stream TV 55\"",
                "Keyless Mobile Access",
                "Fluffy Egyptian Towels",
                "Pillow Menu",
                "In-Room Safe"
            ),
            highlights = listOf("Modern Luxury", "Soundproof Glass", "Plaza View", "Smart Controls"),
            artTheme = "executive"
        )
    )

    private val initialBookings = listOf(
        Booking(
            id = "b-1",
            bookingRef = "STE-849204",
            roomId = "room-1",
            roomName = "Presidential Penthouse Suite",
            roomNumber = "501",
            roomType = RoomType.PENTHOUSE,
            guestName = "Alexander Sterling",
            guestEmail = "alexander.sterling@vanguard.io",
            guestPhone = "+1 (555) 392-8819",
            checkInDate = "Oct 24, 2026",
            checkOutDate = "Oct 27, 2026",
            checkInDayIndex = 24,
            stayDurationDays = 3,
            guestsCount = 2,
            totalNights = 3,
            pricePerNight = 850.0,
            resortFees = 135.0,
            taxes = 306.0,
            totalPrice = 2991.0,
            specialRequests = "High floor, chilled Dom Pérignon upon arrival, late check-in at 8 PM.",
            paymentMethod = "Amex Centurion •••• 1004",
            paymentStatus = "Paid",
            status = BookingStatus.CONFIRMED,
            createdAt = "Oct 19, 2026"
        ),
        Booking(
            id = "b-2",
            bookingRef = "STE-910344",
            roomId = "room-2",
            roomName = "Azure Ocean Villa & Pool",
            roomNumber = "101",
            roomType = RoomType.VILLA,
            guestName = "Marcus Chen",
            guestEmail = "marcus.chen@horizon.tech",
            guestPhone = "+1 (555) 774-2901",
            checkInDate = "Oct 20, 2026",
            checkOutDate = "Oct 24, 2026",
            checkInDayIndex = 20,
            stayDurationDays = 4,
            guestsCount = 3,
            totalNights = 4,
            pricePerNight = 680.0,
            resortFees = 180.0,
            taxes = 326.4,
            totalPrice = 3226.4,
            specialRequests = "Private beach cabana reservation for Thursday.",
            paymentMethod = "Visa Infinite •••• 9921",
            paymentStatus = "Paid",
            status = BookingStatus.CHECKED_IN,
            createdAt = "Oct 14, 2026"
        ),
        Booking(
            id = "b-3",
            bookingRef = "STE-728105",
            roomId = "room-3",
            roomName = "Grand Executive King Suite",
            roomNumber = "304",
            roomType = RoomType.EXECUTIVE,
            guestName = "Eleanor Vance",
            guestEmail = "eleanor.vance@solis.org",
            guestPhone = "+1 (555) 438-1200",
            checkInDate = "Oct 15, 2026",
            checkOutDate = "Oct 18, 2026",
            checkInDayIndex = 15,
            stayDurationDays = 3,
            guestsCount = 1,
            totalNights = 3,
            pricePerNight = 420.0,
            resortFees = 135.0,
            taxes = 151.2,
            totalPrice = 1546.2,
            specialRequests = "Quiet room away from elevators, feather-free pillows.",
            paymentMethod = "Mastercard Black •••• 4422",
            paymentStatus = "Paid",
            status = BookingStatus.COMPLETED,
            createdAt = "Oct 10, 2026"
        ),
        Booking(
            id = "b-4",
            bookingRef = "STE-643019",
            roomId = "room-5",
            roomName = "Riviera Terrace Suite",
            roomNumber = "402",
            roomType = RoomType.SUITE,
            guestName = "Sophia Laurent",
            guestEmail = "sophia.laurent@paris.art",
            guestPhone = "+33 6 40 92 11 88",
            checkInDate = "Oct 26, 2026",
            checkOutDate = "Oct 29, 2026",
            checkInDayIndex = 26,
            stayDurationDays = 3,
            guestsCount = 2,
            totalNights = 3,
            pricePerNight = 380.0,
            resortFees = 135.0,
            taxes = 136.8,
            totalPrice = 1411.8,
            specialRequests = "Anniversary celebration setup with red roses on bed.",
            paymentMethod = "Apple Pay",
            paymentStatus = "Paid",
            status = BookingStatus.CONFIRMED,
            createdAt = "Oct 18, 2026"
        )
    )

    private val _rooms = MutableStateFlow(initialRooms)
    val rooms: StateFlow<List<Room>> = _rooms.asStateFlow()

    private val _bookings = MutableStateFlow(initialBookings)
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun getRoomById(id: String): Room? {
        return _rooms.value.find { it.id == id }
    }

    fun getBookingById(id: String): Booking? {
        return _bookings.value.find { it.id == id }
    }

    fun getBookingByRef(ref: String): Booking? {
        return _bookings.value.find { it.bookingRef == ref }
    }

    fun createBooking(
        roomId: String,
        guestName: String,
        guestEmail: String,
        guestPhone: String,
        checkInDate: String,
        checkOutDate: String,
        checkInDayIndex: Int,
        totalNights: Int,
        guestsCount: Int,
        specialRequests: String,
        paymentMethod: String
    ): Booking {
        val room = getRoomById(roomId) ?: _rooms.value.first()
        val nights = if (totalNights > 0) totalNights else 1
        val roomCost = room.pricePerNight * nights
        val resortFees = 45.0 * nights
        val taxes = (roomCost + resortFees) * 0.12
        val totalPrice = roomCost + resortFees + taxes
        val refCode = "STE-" + (100000 + Random.nextInt(900000)).toString()

        val newBooking = Booking(
            id = "b-${System.currentTimeMillis()}",
            bookingRef = refCode,
            roomId = room.id,
            roomName = room.name,
            roomNumber = room.roomNumber,
            roomType = room.type,
            guestName = guestName.ifBlank { "Guest User" },
            guestEmail = guestEmail.ifBlank { "guest@stayease.com" },
            guestPhone = guestPhone.ifBlank { "+1 (555) 000-1234" },
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            checkInDayIndex = checkInDayIndex,
            stayDurationDays = nights,
            guestsCount = guestsCount,
            totalNights = nights,
            pricePerNight = room.pricePerNight,
            resortFees = resortFees,
            taxes = taxes,
            totalPrice = totalPrice,
            specialRequests = specialRequests,
            paymentMethod = paymentMethod,
            paymentStatus = "Paid",
            status = BookingStatus.CONFIRMED,
            createdAt = "Today"
        )

        _bookings.update { listOf(newBooking) + it }
        return newBooking
    }

    fun cancelBooking(bookingId: String): Boolean {
        _bookings.update { current ->
            current.map {
                if (it.id == bookingId) it.copy(status = BookingStatus.CANCELLED) else it
            }
        }
        return true
    }

    fun updateBookingStatus(bookingId: String, newStatus: BookingStatus) {
        _bookings.update { current ->
            current.map {
                if (it.id == bookingId) it.copy(status = newStatus) else it
            }
        }
    }

    fun toggleRoomMaintenance(roomId: String) {
        _rooms.update { current ->
            current.map {
                if (it.id == roomId) {
                    val newMaint = !it.isMaintenance
                    it.copy(isMaintenance = newMaint, isAvailable = !newMaint)
                } else it
            }
        }
    }

    fun addRoom(newRoom: Room) {
        _rooms.update { it + newRoom }
    }

    fun updateRoom(updatedRoom: Room) {
        _rooms.update { current ->
            current.map { if (it.id == updatedRoom.id) updatedRoom else it }
        }
    }

    fun getStats(): HotelStats {
        val allRooms = _rooms.value
        val allBookings = _bookings.value
        val activeBookings = allBookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.CHECKED_IN }
        
        val totalCount = allRooms.size
        val maintenanceCount = allRooms.count { it.isMaintenance }
        val availableCount = (totalCount - activeBookings.size - maintenanceCount).coerceAtLeast(0)
        val occupancy = if (totalCount > 0) ((activeBookings.size.toDouble() / totalCount) * 100).toInt() else 80

        val revenue = allBookings
            .filter { it.status != BookingStatus.CANCELLED }
            .sumOf { it.totalPrice } + 124000.0 // base hotel monthly revenue

        // Generate occupancy heatmap for 14 days (Oct 18 - Oct 31)
        val heatmap = (18..31).map { day ->
            val dow = when (day % 7) {
                0 -> "Sun"; 1 -> "Mon"; 2 -> "Tue"; 3 -> "Wed"; 4 -> "Thu"; 5 -> "Fri"; else -> "Sat"
            }
            // Occupancy percentage variation
            val occ = when {
                day in 23..25 -> 96 // weekend peak
                day in 30..31 -> 92 // halloween weekend
                day == 20 -> 84     // today
                day % 2 == 0 -> 78
                else -> 82
            }
            DayOccupancy(
                dayNumber = day,
                dayOfWeek = dow,
                occupancyPercentage = occ,
                bookedCount = (totalCount * occ / 100).coerceAtLeast(1),
                totalCount = totalCount
            )
        }

        return HotelStats(
            occupancyRate = occupancy.coerceIn(60, 98),
            occupancyDelta = "+6% vs last week",
            bookingsToday = 14,
            bookingsTodayDelta = "+3 reservations today",
            revenueThisMonth = revenue,
            revenueDelta = "+18.4% MoM",
            availableRooms = availableCount,
            totalRooms = totalCount,
            heatmapDays = heatmap
        )
    }
}
