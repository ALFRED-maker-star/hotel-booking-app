package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KingBed
import androidx.compose.material.icons.outlined.Luggage
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.IvoryBackground
import com.example.ui.theme.IvoryCard
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateMuted

enum class AppDestination {
    GUEST_HOME,
    GUEST_ROOMS,
    GUEST_ROOM_DETAIL,
    GUEST_BOOK_ROOM,
    GUEST_CHECKOUT,
    GUEST_CONFIRMATION,
    GUEST_MY_BOOKINGS,
    ADMIN_LOGIN,
    ADMIN_DASHBOARD,
    ADMIN_ROOMS,
    ADMIN_CALENDAR,
    ADMIN_BOOKING_DETAIL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StayEaseTopBar(
    currentDestination: AppDestination,
    isAdminLoggedIn: Boolean,
    onAdminLoginClick: () -> Unit,
    onSwitchToGuest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdminScreen = currentDestination in listOf(
        AppDestination.ADMIN_DASHBOARD,
        AppDestination.ADMIN_ROOMS,
        AppDestination.ADMIN_CALENDAR,
        AppDestination.ADMIN_BOOKING_DETAIL
    )

    Surface(
        color = if (isAdminScreen) NavyDark else IvoryBackground,
        shadowElevation = if (isAdminScreen) 4.dp else 1.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // StayEase Luxury Monogram Logo
            Surface(
                shape = CircleShape,
                color = if (isAdminScreen) NavyPrimary else NavyPrimary,
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "S",
                        color = GoldAccent,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            // Brand Title
            androidx.compose.foundation.layout.Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "STAYEASE",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        letterSpacing = 1.2.sp,
                        color = if (isAdminScreen) Color.White else NavyPrimary
                    )
                    if (isAdminScreen) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = GoldAccent,
                            modifier = Modifier.padding(start = 6.dp)
                        ) {
                            Text(
                                text = "ADMIN",
                                color = NavyDark,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = if (isAdminScreen) "Staff Management Console" else "Grand Resort & Riviera Suites",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = if (isAdminScreen) GoldAccent else SlateMuted
                )
            }

            // Mode switcher button
            if (isAdminScreen) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = NavyPrimary,
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                ) {
                    IconButton(
                        onClick = onSwitchToGuest,
                        modifier = Modifier.testTag("switch_to_guest_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Switch to Guest View",
                            tint = GoldAccent
                        )
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = IvoryCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                ) {
                    IconButton(
                        onClick = onAdminLoginClick,
                        modifier = Modifier.testTag("admin_portal_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AdminPanelSettings,
                            contentDescription = "Admin Portal",
                            tint = NavyPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StayEaseBottomNavigationBar(
    currentDestination: AppDestination,
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val isAdmin = currentDestination in listOf(
        AppDestination.ADMIN_DASHBOARD,
        AppDestination.ADMIN_ROOMS,
        AppDestination.ADMIN_CALENDAR,
        AppDestination.ADMIN_BOOKING_DETAIL
    )

    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .testTag("bottom_nav_bar"),
        containerColor = if (isAdmin) NavyDark else IvoryCard,
        tonalElevation = 8.dp
    ) {
        if (!isAdmin) {
            NavigationBarItem(
                selected = currentDestination == AppDestination.GUEST_HOME,
                onClick = { onNavigate(AppDestination.GUEST_HOME) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == AppDestination.GUEST_HOME) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = "Home"
                    )
                },
                label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyPrimary,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_guest_home")
            )

            NavigationBarItem(
                selected = currentDestination == AppDestination.GUEST_ROOMS,
                onClick = { onNavigate(AppDestination.GUEST_ROOMS) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == AppDestination.GUEST_ROOMS) Icons.Filled.KingBed else Icons.Outlined.KingBed,
                        contentDescription = "Rooms"
                    )
                },
                label = { Text("Rooms", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyPrimary,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_guest_rooms")
            )

            NavigationBarItem(
                selected = currentDestination == AppDestination.GUEST_MY_BOOKINGS,
                onClick = { onNavigate(AppDestination.GUEST_MY_BOOKINGS) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == AppDestination.GUEST_MY_BOOKINGS) Icons.Filled.Luggage else Icons.Outlined.Luggage,
                        contentDescription = "My Stays"
                    )
                },
                label = { Text("My Stays", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyPrimary,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_guest_bookings")
            )

            NavigationBarItem(
                selected = currentDestination == AppDestination.ADMIN_LOGIN,
                onClick = { onNavigate(AppDestination.ADMIN_LOGIN) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.AdminPanelSettings,
                        contentDescription = "Staff Portal"
                    )
                },
                label = { Text("Staff", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = NavyPrimary,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_admin_login")
            )
        } else {
            // Admin Navigation
            NavigationBarItem(
                selected = currentDestination == AppDestination.ADMIN_DASHBOARD,
                onClick = { onNavigate(AppDestination.ADMIN_DASHBOARD) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == AppDestination.ADMIN_DASHBOARD) Icons.Filled.Dashboard else Icons.Outlined.Dashboard,
                        contentDescription = "Dashboard"
                    )
                },
                label = { Text("Dashboard", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = GoldAccent,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_admin_dashboard")
            )

            NavigationBarItem(
                selected = currentDestination == AppDestination.ADMIN_ROOMS,
                onClick = { onNavigate(AppDestination.ADMIN_ROOMS) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == AppDestination.ADMIN_ROOMS) Icons.Filled.MeetingRoom else Icons.Outlined.MeetingRoom,
                        contentDescription = "Rooms"
                    )
                },
                label = { Text("Rooms", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = GoldAccent,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_admin_rooms")
            )

            NavigationBarItem(
                selected = currentDestination == AppDestination.ADMIN_CALENDAR,
                onClick = { onNavigate(AppDestination.ADMIN_CALENDAR) },
                icon = {
                    Icon(
                        imageVector = if (currentDestination == AppDestination.ADMIN_CALENDAR) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
                        contentDescription = "Bookings"
                    )
                },
                label = { Text("Calendar", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = GoldAccent,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_admin_calendar")
            )

            NavigationBarItem(
                selected = false,
                onClick = { onNavigate(AppDestination.GUEST_HOME) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Guest View"
                    )
                },
                label = { Text("Guest App", style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavyDark,
                    selectedTextColor = GoldAccent,
                    indicatorColor = GoldAccent,
                    unselectedIconColor = SlateMuted,
                    unselectedTextColor = SlateMuted
                ),
                modifier = Modifier.testTag("nav_admin_exit")
            )
        }
    }
}
