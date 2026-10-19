package com.example.appfulbo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appfulbo.ui.theme.PrimaryColor

@Composable
fun ButtonHour(
    value: String,
    selectHour: Set<String>,
    onToggleHour: (String) -> Unit,
    isReserved: Boolean
) {
    val isSelected = value in selectHour

    val containerColor = when {
        isReserved -> Color(0xFFEEEEEE)
        isSelected -> PrimaryColor
        else -> Color.White
    }

    val contentColor = when {
        isReserved -> Color(0xFF9E9E9E)
        isSelected -> Color.White
        else -> Color.DarkGray
    }

    val border = when {
        isReserved -> BorderStroke(1.dp, Color(0xFFE0E0E0))
        isSelected -> null
        else -> BorderStroke(1.5.dp, PrimaryColor.copy(alpha = 0.6f))
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isReserved) {
                onToggleHour(value)
            },
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = border,
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Box(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                color = contentColor,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                textDecoration = if (isReserved) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}
