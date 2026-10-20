package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.StayEaseRepository
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.model.HotelStats
import com.example.model.Room
import com.example.model.RoomType
import com.example.ui.components.AppDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BookingDraftState(
    val roomId: String = "",
    val checkInDate: String = "Oct 24, 2026",
    val checkOutDate: String = "Oct 27, 2026",
    val checkInDayIndex: Int = 24,
    val totalNights: Int = 3,
    val guestsCount: Int = 2,
    val guestName: String = "Alexander Sterling",
    val guestEmail: String = "alexander.sterling@vanguard.io",
    val guestPhone: String = "+1 (555) 392-8819",
    val specialRequests: String = "High floor, quiet room with champagne.",
    val paymentMethod: String = "Visa •••• 8842"
)

class StayEaseViewModel(
    private val repository: StayEaseRepository = StayEaseRepository()
) : ViewModel() {

    private val _currentDestination = MutableStateFlow(AppDestination.GUEST_HOME)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    private val _destinationBackStack = MutableStateFlow<List<AppDestination>>(listOf(AppDestination.GUEST_HOME))

    private val _selectedRoomId = MutableStateFlow<String?>("room-1")
    val selectedRoomId: StateFlow<String?> = _selectedRoomId.asStateFlow()

    private val _selectedBookingId = MutableStateFlow<String?>("b-1")
    val selectedBookingId: StateFlow<String?> = _selectedBookingId.asStateFlow()

    private val _lastConfirmedBooking = MutableStateFlow<Booking?>(null)
    val lastConfirmedBooking: StateFlow<Booking?> = _lastConfirmedBooking.asStateFlow()

    // Filters
    private val _selectedTypeFilter = MutableStateFlow(RoomType.ALL)
    val selectedTypeFilter: StateFlow<RoomType> = _selectedTypeFilter.asStateFlow()

    private val _maxPriceFilter = MutableStateFlow<Double>(1000.0)
    val maxPriceFilter: StateFlow<Double> = _maxPriceFilter.asStateFlow()

    private val _minCapacityFilter = MutableStateFlow(1)
    val minCapacityFilter: StateFlow<Int> = _minCapacityFilter.asStateFlow()

    // Search query on home
    private val _searchCheckIn = MutableStateFlow("Oct 24, 2026")
    val searchCheckIn: StateFlow<String> = _searchCheckIn.asStateFlow()

    private val _searchCheckOut = MutableStateFlow("Oct 27, 2026")
    val searchCheckOut: StateFlow<String> = _searchCheckOut.asStateFlow()

    private val _searchGuests = MutableStateFlow(2)
    val searchGuests: StateFlow<Int> = _searchGuests.asStateFlow()

    // Booking in-progress draft
    private val _bookingDraft = MutableStateFlow(BookingDraftState())
    val bookingDraft: StateFlow<BookingDraftState> = _bookingDraft.asStateFlow()

    // Admin State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _adminNotification = MutableStateFlow<String?>(null)
    val adminNotification: StateFlow<String?> = _adminNotification.asStateFlow()

    val rooms: StateFlow<List<Room>> = repository.rooms
    val bookings: StateFlow<List<Booking>> = repository.bookings

    // Filtered rooms
    val filteredRooms: StateFlow<List<Room>> = combine(
        rooms,
        _selectedTypeFilter,
        _maxPriceFilter,
        _minCapacityFilter
    ) { roomList, typeFilter, maxPrice, minCap ->
        roomList.filter { room ->
            (typeFilter == RoomType.ALL || room.type == typeFilter) &&
            room.pricePerNight <= maxPrice &&
            room.capacity >= minCap
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun navigateTo(destination: AppDestination) {
        _destinationBackStack.update { it + destination }
        _currentDestination.value = destination
    }

    fun navigateBack(): Boolean {
        val stack = _destinationBackStack.value
        if (stack.size > 1) {
            val newStack = stack.dropLast(1)
            _destinationBackStack.value = newStack
            _currentDestination.value = newStack.last()
            return true
        }
        return false
    }

    fun openRoomDetail(roomId: String) {
        _selectedRoomId.value = roomId
        navigateTo(AppDestination.GUEST_ROOM_DETAIL)
    }

    fun startBookingFlow(roomId: String) {
        _selectedRoomId.value = roomId
        _bookingDraft.update {
            it.copy(
                roomId = roomId,
                checkInDate = _searchCheckIn.value,
                checkOutDate = _searchCheckOut.value,
                guestsCount = _searchGuests.value,
                totalNights = 3
            )
        }
        navigateTo(AppDestination.GUEST_BOOK_ROOM)
    }

    fun updateBookingDates(checkIn: String, checkOut: String, nights: Int, guests: Int) {
        _bookingDraft.update {
            it.copy(
                checkInDate = checkIn,
                checkOutDate = checkOut,
                totalNights = nights.coerceAtLeast(1),
                guestsCount = guests
            )
        }
    }

    fun proceedToCheckout() {
        navigateTo(AppDestination.GUEST_CHECKOUT)
    }

    fun updateGuestDetails(name: String, email: String, phone: String, requests: String, payment: String) {
        _bookingDraft.update {
            it.copy(
                guestName = name,
                guestEmail = email,
                guestPhone = phone,
                specialRequests = requests,
                paymentMethod = payment
            )
        }
    }

    fun confirmAndPay() {
        val draft = _bookingDraft.value
        val newBooking = repository.createBooking(
            roomId = draft.roomId.ifBlank { _selectedRoomId.value ?: "room-1" },
            guestName = draft.guestName,
            guestEmail = draft.guestEmail,
            guestPhone = draft.guestPhone,
            checkInDate = draft.checkInDate,
            checkOutDate = draft.checkOutDate,
            checkInDayIndex = draft.checkInDayIndex,
            totalNights = draft.totalNights,
            guestsCount = draft.guestsCount,
            specialRequests = draft.specialRequests,
            paymentMethod = draft.paymentMethod
        )
        _lastConfirmedBooking.value = newBooking
        _selectedBookingId.value = newBooking.id
        navigateTo(AppDestination.GUEST_CONFIRMATION)
    }

    fun openBookingDetail(bookingId: String, fromAdmin: Boolean = false) {
        _selectedBookingId.value = bookingId
        if (fromAdmin) {
            navigateTo(AppDestination.ADMIN_BOOKING_DETAIL)
        } else {
            navigateTo(AppDestination.GUEST_MY_BOOKINGS)
        }
    }

    fun cancelBooking(bookingId: String) {
        repository.cancelBooking(bookingId)
    }

    fun updateBookingStatus(bookingId: String, newStatus: BookingStatus) {
        repository.updateBookingStatus(bookingId, newStatus)
    }

    fun toggleRoomMaintenance(roomId: String) {
        repository.toggleRoomMaintenance(roomId)
    }

    fun addNewRoom(newRoom: Room) {
        repository.addRoom(newRoom)
    }

    fun updateRoom(room: Room) {
        repository.updateRoom(room)
    }

    // Filter controls
    fun setTypeFilter(type: RoomType) {
        _selectedTypeFilter.value = type
    }

    fun setMaxPriceFilter(price: Double) {
        _maxPriceFilter.value = price
    }

    fun setMinCapacityFilter(cap: Int) {
        _minCapacityFilter.value = cap
    }

    fun updateSearchCriteria(checkIn: String, checkOut: String, guests: Int) {
        _searchCheckIn.value = checkIn
        _searchCheckOut.value = checkOut
        _searchGuests.value = guests
    }

    // Admin Auth
    fun loginAdmin(password: String): Boolean {
        // Any password or demo login will grant access
        _isAdminLoggedIn.value = true
        navigateTo(AppDestination.ADMIN_DASHBOARD)
        return true
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        navigateTo(AppDestination.GUEST_HOME)
    }

    fun getStats(): HotelStats {
        return repository.getStats()
    }

    fun getSelectedRoom(): Room? {
        val id = _selectedRoomId.value ?: return null
        return repository.getRoomById(id)
    }

    fun getSelectedBooking(): Booking? {
        val id = _selectedBookingId.value ?: return null
        return repository.getBookingById(id)
    }
}
