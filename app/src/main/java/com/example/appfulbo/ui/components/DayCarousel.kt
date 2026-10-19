package com.example.appfulbo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appfulbo.model.DaysOfWeek
import com.example.appfulbo.ui.theme.PrimaryColor
import com.example.appfulbo.ui.theme.TextColorSecondary

@ExperimentalMaterial3Api
@Composable
fun DayCarousel(
    select: Int,
    days: List<DaysOfWeek>,
    onDaySelect: (i: Int, day: DaysOfWeek) -> Unit,
    selectColor: Color = PrimaryColor,
    unselectColor: Color = TextColorSecondary,
    modifier: Modifier = Modifier,
    preferredItemWidth: Dp = 80.dp
) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { days.size },
        preferredItemWidth = preferredItemWidth,
        modifier = modifier,
    ) { i ->
        val day = days[i]
        val isSelect = (select == i)
        val color = if (isSelect) selectColor else unselectColor
        Column(
            Modifier
                .size(width = preferredItemWidth, height = 80.dp)
                .clickable {
                    if (!isSelect) {  onDaySelect(i, day)}
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Text(
                day.label,
                fontSize = 16.sp,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                day.number,
                fontSize = 35.sp,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                day.month,
                fontSize = 16.sp,
                color = color,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

