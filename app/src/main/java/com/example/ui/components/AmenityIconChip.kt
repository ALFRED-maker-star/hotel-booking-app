package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.IvoryCard
import com.example.ui.theme.IvoryMuted
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.SlateMuted

fun getAmenityIcon(name: String): ImageVector {
    val lower = name.lowercase()
    return when {
        lower.contains("pool") || lower.contains("water") -> Icons.Default.Pool
        lower.contains("spa") || lower.contains("bath") || lower.contains("tub") -> Icons.Default.Spa
        lower.contains("dining") || lower.contains("chef") || lower.contains("restaurant") -> Icons.Default.Restaurant
        lower.contains("wifi") || lower.contains("wi-fi") || lower.contains("fiber") -> Icons.Default.Wifi
        lower.contains("gym") || lower.contains("fitness") -> Icons.Default.FitnessCenter
        lower.contains("butler") || lower.contains("service") || lower.contains("concierge") -> Icons.Default.RoomService
        lower.contains("bar") || lower.contains("wine") || lower.contains("champagne") -> Icons.Default.LocalBar
        lower.contains("coffee") || lower.contains("nespresso") -> Icons.Default.Coffee
        lower.contains("bed") || lower.contains("king") || lower.contains("linen") -> Icons.Default.Bed
        lower.contains("tv") || lower.contains("stream") -> Icons.Default.Tv
        else -> Icons.Default.Bathtub
    }
}

@Composable
fun AmenityIconChip(
    title: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .let { if (onClick != null) it.clickable { onClick() } else it },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = IvoryMuted,
            shadowElevation = 1.dp,
            modifier = Modifier.size(52.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = GoldAccent,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = NavyPrimary,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
fun AmenitySmallTag(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = IvoryMuted,
        modifier = modifier
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getAmenityIcon(text),
                contentDescription = null,
                tint = NavyPrimary,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = NavyDark,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
