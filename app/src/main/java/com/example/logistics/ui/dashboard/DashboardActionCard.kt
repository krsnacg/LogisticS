package com.example.logistics.ui.dashboard

import androidx.compose.runtime.Composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Alignment
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DashboardActionCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        modifier = modifier
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            }
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}