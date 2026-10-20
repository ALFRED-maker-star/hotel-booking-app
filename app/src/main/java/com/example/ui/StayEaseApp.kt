package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppDestination
import com.example.ui.components.StayEaseBottomNavigationBar
import com.example.ui.components.StayEaseTopBar
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.screens.admin.AdminLoginScreen
import com.example.ui.screens.admin.BookingDetailScreen
import com.example.ui.screens.admin.BookingsCalendarScreen
import com.example.ui.screens.admin.RoomsManagementScreen
import com.example.ui.screens.guest.BookRoomScreen
import com.example.ui.screens.guest.BookingConfirmationScreen
import com.example.ui.screens.guest.CheckoutScreen
import com.example.ui.screens.guest.HomeScreen
import com.example.ui.screens.guest.MyBookingsScreen
import com.example.ui.screens.guest.RoomDetailScreen
import com.example.ui.screens.guest.RoomListingsScreen

@Composable
fun StayEaseApp(
    viewModel: StayEaseViewModel = viewModel()
) {
    val currentDestination by viewModel.currentDestination.collectAsState()
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()

    val showTopBar = currentDestination !in listOf(
        AppDestination.ADMIN_LOGIN,
        AppDestination.GUEST_CONFIRMATION
    )

    val showBottomBar = currentDestination in listOf(
        AppDestination.GUEST_HOME,
        AppDestination.GUEST_ROOMS,
        AppDestination.GUEST_MY_BOOKINGS,
        AppDestination.ADMIN_DASHBOARD,
        AppDestination.ADMIN_ROOMS,
        AppDestination.ADMIN_CALENDAR
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            if (showTopBar) {
                StayEaseTopBar(
                    currentDestination = currentDestination,
                    isAdminLoggedIn = isAdminLoggedIn,
                    onAdminLoginClick = {
                        if (isAdminLoggedIn) {
                            viewModel.navigateTo(AppDestination.ADMIN_DASHBOARD)
                        } else {
                            viewModel.navigateTo(AppDestination.ADMIN_LOGIN)
                        }
                    },
                    onSwitchToGuest = {
                        viewModel.navigateTo(AppDestination.GUEST_HOME)
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                StayEaseBottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = { dest ->
                        if (dest == AppDestination.ADMIN_LOGIN && isAdminLoggedIn) {
                            viewModel.navigateTo(AppDestination.ADMIN_DASHBOARD)
                        } else {
                            viewModel.navigateTo(dest)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { target ->
                when (target) {
                    AppDestination.GUEST_HOME -> HomeScreen(viewModel = viewModel)
                    AppDestination.GUEST_ROOMS -> RoomListingsScreen(viewModel = viewModel)
                    AppDestination.GUEST_ROOM_DETAIL -> RoomDetailScreen(viewModel = viewModel)
                    AppDestination.GUEST_BOOK_ROOM -> BookRoomScreen(viewModel = viewModel)
                    AppDestination.GUEST_CHECKOUT -> CheckoutScreen(viewModel = viewModel)
                    AppDestination.GUEST_CONFIRMATION -> BookingConfirmationScreen(viewModel = viewModel)
                    AppDestination.GUEST_MY_BOOKINGS -> MyBookingsScreen(viewModel = viewModel)
                    AppDestination.ADMIN_LOGIN -> AdminLoginScreen(viewModel = viewModel)
                    AppDestination.ADMIN_DASHBOARD -> AdminDashboardScreen(viewModel = viewModel)
                    AppDestination.ADMIN_ROOMS -> RoomsManagementScreen(viewModel = viewModel)
                    AppDestination.ADMIN_CALENDAR -> BookingsCalendarScreen(viewModel = viewModel)
                    AppDestination.ADMIN_BOOKING_DETAIL -> BookingDetailScreen(viewModel = viewModel)
                }
            }
        }
    }
}
