package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.NavyDark
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.NavySurface

@Composable
fun HotelHeroVisual(
    modifier: Modifier = Modifier,
    height: Dp = 240.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Twilight sky gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1128),
                        Color(0xFF1C2A4A),
                        Color(0xFF2C3E6B),
                        Color(0xFF634D41)
                    )
                )
            )

            // Distant hills / horizon
            val hillPath = Path().apply {
                moveTo(0f, h * 0.65f)
                cubicTo(w * 0.25f, h * 0.60f, w * 0.45f, h * 0.68f, w * 0.7f, h * 0.62f)
                cubicTo(w * 0.85f, h * 0.58f, w * 0.95f, h * 0.65f, w, h * 0.63f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(hillPath, brush = Brush.verticalGradient(listOf(Color(0xFF1B263B), Color(0xFF0F172A))))

            // Hotel Main Building Silhouette
            val buildingLeft = w * 0.12f
            val buildingRight = w * 0.88f
            val buildingTop = h * 0.32f
            val buildingWidth = buildingRight - buildingLeft
            val buildingHeight = h - buildingTop

            // Grand facade
            drawRoundRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(buildingLeft, buildingTop),
                size = Size(buildingWidth, buildingHeight),
                cornerRadius = CornerRadius(8f, 8f)
            )

            // Grand pediment / crown
            val pedimentPath = Path().apply {
                moveTo(buildingLeft + buildingWidth * 0.25f, buildingTop)
                lineTo(w * 0.5f, buildingTop - h * 0.12f)
                lineTo(buildingLeft + buildingWidth * 0.75f, buildingTop)
                close()
            }
            drawPath(pedimentPath, color = Color(0xFF1E293B))
            drawPath(pedimentPath, color = GoldAccent, style = Stroke(width = 2.5f))

            // Architectural warm glowing windows (3 tiers)
            val rows = 4
            val cols = 7
            val padX = buildingWidth / (cols + 1)
            val padY = (buildingHeight * 0.6f) / (rows + 1)

            for (r in 0 until rows) {
                for (c in 0 until cols) {
                    val wx = buildingLeft + padX * (c + 1) - 6f
                    val wy = buildingTop + h * 0.04f + padY * (r + 1)
                    val glowColor = if ((r + c) % 3 == 0) Color(0xFFFFE082) else Color(0xFFFFD54F)
                    drawRoundRect(
                        color = glowColor.copy(alpha = 0.85f),
                        topLeft = Offset(wx, wy),
                        size = Size(14f, 22f),
                        cornerRadius = CornerRadius(4f, 4f)
                    )
                }
            }

            // Grand Portico with Warm Golden Ambient Chandelier
            val porticoW = buildingWidth * 0.44f
            val porticoX = w * 0.5f - porticoW * 0.5f
            val porticoY = h * 0.62f
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFFFEEAA), Color(0xFFD4A017).copy(alpha = 0.5f), Color.Transparent),
                    center = Offset(w * 0.5f, porticoY + 20f),
                    radius = 90f
                )
            )

            // Grand Pillars
            val pillarW = 8f
            for (p in 0..4) {
                val px = porticoX + (porticoW / 4) * p - pillarW * 0.5f
                drawRect(
                    color = Color(0xFFE2E8F0),
                    topLeft = Offset(px, porticoY),
                    size = Size(pillarW, h - porticoY)
                )
            }

            // Fountain & Driveway water reflections
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF67E8F9).copy(alpha = 0.7f), Color(0xFF0369A1).copy(alpha = 0.4f), Color.Transparent),
                    center = Offset(w * 0.5f, h * 0.88f),
                    radius = 80f
                ),
                topLeft = Offset(w * 0.35f, h * 0.82f),
                size = Size(w * 0.30f, h * 0.14f)
            )

            // Palm trees silhouettes on flanks
            fun drawPalm(cx: Float, cy: Float, scale: Float) {
                // trunk
                drawLine(
                    color = Color(0xFF0F172A),
                    start = Offset(cx, cy + 50f * scale),
                    end = Offset(cx - 6f, cy),
                    strokeWidth = 5f * scale
                )
                // fronds
                for (a in 0..6) {
                    val angle = a * (Math.PI / 4) - Math.PI * 0.8
                    val ex = cx + (Math.cos(angle) * 32 * scale).toFloat()
                    val ey = cy + (Math.sin(angle) * 22 * scale).toFloat()
                    drawLine(
                        color = Color(0xFF1E293B),
                        start = Offset(cx, cy),
                        end = Offset(ex, ey),
                        strokeWidth = 3f * scale
                    )
                }
            }

            drawPalm(w * 0.08f, h * 0.68f, 1.1f)
            drawPalm(w * 0.92f, h * 0.68f, 1.1f)

            // Ambient vignette
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color(0x66000000), Color(0xCC0F172A)),
                    startY = h * 0.5f,
                    endY = h
                )
            )
        }
    }
}

@Composable
fun RoomPhotoVisual(
    artTheme: String,
    modifier: Modifier = Modifier,
    height: Dp = 180.dp,
    badgeText: String? = null,
    rating: Double = 4.9
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            when (artTheme) {
                "presidential", "penthouse" -> {
                    // Skyline Penthouse at Night / Golden Hour
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF0B192C), Color(0xFF1E3E62), Color(0xFF423838), Color(0xFFB87C4C))
                        )
                    )
                    // Floor to ceiling panoramic glass mullions
                    for (i in 1..4) {
                        drawLine(
                            color = Color(0x33FFFFFF),
                            start = Offset(w * (i * 0.2f), 0f),
                            end = Offset(w * (i * 0.2f), h),
                            strokeWidth = 3f
                        )
                    }
                    // Distant skyline buildings
                    for (b in 0..12) {
                        val bw = 24f
                        val bx = b * 30f + 10f
                        val bh = (40 + (b * 19) % 70).toFloat()
                        drawRect(
                            color = Color(0xFF0A1128).copy(alpha = 0.85f),
                            topLeft = Offset(bx, h * 0.55f - bh),
                            size = Size(bw, bh)
                        )
                        // Tiny skyscraper window lights
                        for (win in 0..3) {
                            drawCircle(
                                color = Color(0xFFFFD54F).copy(alpha = 0.7f),
                                radius = 1.8f,
                                center = Offset(bx + 6f + (win % 2) * 10f, h * 0.55f - bh + 10f + win * 8f)
                            )
                        }
                    }
                    // Luxury King Bed & warm sconces
                    drawRoundRect(
                        brush = Brush.horizontalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF1F5F9), Color(0xFFE2E8F0))),
                        topLeft = Offset(w * 0.28f, h * 0.58f),
                        size = Size(w * 0.44f, h * 0.38f),
                        cornerRadius = CornerRadius(12f, 12f)
                    )
                    // Gold decorative bed runner
                    drawRect(
                        brush = Brush.horizontalGradient(listOf(GoldAccent, GoldLight)),
                        topLeft = Offset(w * 0.28f, h * 0.82f),
                        size = Size(w * 0.44f, 16f)
                    )
                    // Bed headboard rich mahogany
                    drawRoundRect(
                        color = Color(0xFF3E2723),
                        topLeft = Offset(w * 0.24f, h * 0.46f),
                        size = Size(w * 0.52f, h * 0.16f),
                        cornerRadius = CornerRadius(8f, 8f)
                    )
                    // Warm golden sconce glow
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFFFFE082), Color.Transparent), center = Offset(w * 0.22f, h * 0.48f), radius = 35f)
                    )
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFFFFE082), Color.Transparent), center = Offset(w * 0.78f, h * 0.48f), radius = 35f)
                    )
                }
                "ocean" -> {
                    // Sun-drenched azure sea & private pool terrace
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF38BDF8), Color(0xFF0284C7), Color(0xFF0369A1), Color(0xFF0C4A6E))
                        )
                    )
                    // Ocean ripples
                    for (wave in 0..5) {
                        val wy = h * (0.2f + wave * 0.08f)
                        drawLine(
                            color = Color(0x33FFFFFF),
                            start = Offset(0f, wy),
                            end = Offset(w, wy + 4f),
                            strokeWidth = 2f
                        )
                    }
                    // Private plunge pool in foreground
                    val poolPath = Path().apply {
                        moveTo(0f, h * 0.62f)
                        cubicTo(w * 0.4f, h * 0.55f, w * 0.6f, h * 0.7f, w, h * 0.6f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(
                        poolPath,
                        brush = Brush.verticalGradient(listOf(Color(0xFF06B6D4), Color(0xFF0891B2), Color(0xFF155E75)))
                    )
                    // Teak deck and sun loungers
                    drawRect(
                        color = Color(0xFF92400E).copy(alpha = 0.9f),
                        topLeft = Offset(w * 0.55f, h * 0.72f),
                        size = Size(w * 0.42f, h * 0.24f)
                    )
                    drawRoundRect(
                        color = Color(0xFFFAFAFA),
                        topLeft = Offset(w * 0.6f, h * 0.75f),
                        size = Size(w * 0.32f, 18f),
                        cornerRadius = CornerRadius(4f, 4f)
                    )
                    // Sun glare
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0x88FFFFFF), Color.Transparent), center = Offset(w * 0.85f, h * 0.15f), radius = 70f)
                    )
                }
                "garden" -> {
                    // Botanical lush garden villa
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFFE0E7FF), Color(0xFF86EFAC), Color(0xFF16A34A), Color(0xFF14532D))
                        )
                    )
                    // Lush tropical foliage leaves
                    val leafColors = listOf(Color(0xFF15803D), Color(0xFF166534), Color(0xFF14532D), Color(0xFF4ADE80))
                    for (leaf in 0..10) {
                        val lx = (leaf * 38f) % w
                        val ly = h * 0.35f + (leaf * 14f) % (h * 0.5f)
                        drawOval(
                            color = leafColors[leaf % leafColors.size].copy(alpha = 0.85f),
                            topLeft = Offset(lx, ly),
                            size = Size(65f, 32f)
                        )
                    }
                    // Stone patio villa entrance
                    drawRoundRect(
                        color = Color(0xFFD6D3D1),
                        topLeft = Offset(w * 0.15f, h * 0.65f),
                        size = Size(w * 0.7f, h * 0.35f),
                        cornerRadius = CornerRadius(10f, 10f)
                    )
                    // Warm villa lanterns
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFFFFD54F), Color.Transparent), center = Offset(w * 0.3f, h * 0.58f), radius = 40f)
                    )
                }
                else -> {
                    // Executive King Suite warm interior
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFF1E293B), Color(0xFF334155), Color(0xFF475569), Color(0xFF64748B))
                        )
                    )
                    // King bed with layered textures
                    drawRoundRect(
                        color = Color(0xFF0F172A),
                        topLeft = Offset(w * 0.2f, h * 0.45f),
                        size = Size(w * 0.6f, h * 0.18f),
                        cornerRadius = CornerRadius(8f, 8f)
                    )
                    drawRoundRect(
                        brush = Brush.horizontalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF8FAFC))),
                        topLeft = Offset(w * 0.24f, h * 0.56f),
                        size = Size(w * 0.52f, h * 0.40f),
                        cornerRadius = CornerRadius(10f, 10f)
                    )
                    // Gold Accent throw
                    drawRect(
                        color = GoldAccent,
                        topLeft = Offset(w * 0.24f, h * 0.80f),
                        size = Size(w * 0.52f, 14f)
                    )
                    // Warm bedside lamps
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFFFFE082), Color.Transparent), center = Offset(w * 0.16f, h * 0.48f), radius = 38f)
                    )
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFFFFE082), Color.Transparent), center = Offset(w * 0.84f, h * 0.48f), radius = 38f)
                    )
                }
            }

            // Bottom subtle vignette
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color(0x99000000)),
                    startY = h * 0.65f,
                    endY = h
                )
            )
        }

        // Availability / special badge overlay (Top-Left)
        if (badgeText != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                shape = RoundedCornerShape(8.dp),
                color = NavyDark.copy(alpha = 0.88f),
                shadowElevation = 4.dp
            ) {
                Text(
                    text = badgeText,
                    color = GoldLight,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Rating pill overlay (Top-Right)
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            color = NavyPrimary.copy(alpha = 0.85f),
            shadowElevation = 4.dp
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = GoldAccent,
                    modifier = Modifier.padding(end = 4.dp).height(14.dp)
                )
                Text(
                    text = String.format("%.2f", rating),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
