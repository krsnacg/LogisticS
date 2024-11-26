package com.example.logistics.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationOptions(
    val items: List<String>,
    val routes: List<String>,
    val selectedIcons: List<ImageVector>,
    val unselectedIcons: List<ImageVector>
)
