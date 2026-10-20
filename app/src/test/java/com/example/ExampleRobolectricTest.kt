package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.StayEaseRepository
import com.example.model.BookingStatus
import com.example.model.RoomType
import com.example.ui.StayEaseViewModel
import com.example.ui.components.AppDestination
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches StayEase`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("StayEase", appName)
    }

    @Test
    fun `repository loads initial rooms and calculates stats`() {
        val repo = StayEaseRepository()
        val rooms = repo.rooms.value
        assertTrue(rooms.isNotEmpty())

        val penthouse = rooms.find { it.type == RoomType.PENTHOUSE }
        assertNotNull(penthouse)
        assertEquals("Presidential Penthouse Suite", penthouse?.name)

        val stats = repo.getStats()
        assertTrue(stats.occupancyRate > 0)
        assertTrue(stats.revenueThisMonth > 0)
    }

    @Test
    fun `create booking flow calculates total and persists to bookings`() {
        val repo = StayEaseRepository()
        val initialCount = repo.bookings.value.size

        val newBooking = repo.createBooking(
            roomId = "room-1",
            guestName = "Lady Genevieve",
            guestEmail = "genevieve@stayease.com",
            guestPhone = "+1 555 999 8888",
            checkInDate = "Nov 10, 2026",
            checkOutDate = "Nov 14, 2026",
            checkInDayIndex = 10,
            totalNights = 4,
            guestsCount = 2,
            specialRequests = "Champagne on ice",
            paymentMethod = "Visa •••• 1234"
        )

        assertEquals(initialCount + 1, repo.bookings.value.size)
        assertEquals("Lady Genevieve", newBooking.guestName)
        assertEquals(BookingStatus.CONFIRMED, newBooking.status)
        assertTrue(newBooking.totalPrice > 0)
    }

    @Test
    fun `admin check-in and room maintenance toggles`() {
        val repo = StayEaseRepository()
        val booking = repo.bookings.value.first()
        
        repo.updateBookingStatus(booking.id, BookingStatus.CHECKED_IN)
        val updatedBooking = repo.getBookingById(booking.id)
        assertEquals(BookingStatus.CHECKED_IN, updatedBooking?.status)

        val room = repo.rooms.value.first()
        val initialMaint = room.isMaintenance
        repo.toggleRoomMaintenance(room.id)
        val updatedRoom = repo.getRoomById(room.id)
        assertEquals(!initialMaint, updatedRoom?.isMaintenance)
    }
}
