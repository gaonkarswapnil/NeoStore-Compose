package com.example.neostorecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.neostorecompose.ui.theme.OrangeVariant
import kotlinx.coroutines.delay

@Composable
fun AutoSlidingImageSliderComp(
    imageUrls: List<String>,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { imageUrls.size })
    val imageCount = imageUrls.size

    // Auto-scroll logic
    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        val nextPage = (pagerState.currentPage + 1) % imageCount
        pagerState.animateScrollToPage(nextPage)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { page ->
            AsyncImage(
                model = imageUrls[page],
                contentDescription = "Image $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        CustomPagerIndicator(
            totalDots = imageCount,
            selectedIndex = pagerState.currentPage
        )
    }
}

@Composable
fun CustomPagerIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = OrangeVariant,
    inactiveColor: Color = Color.LightGray,
    dotSize: Dp = 8.dp,
    spacing: Dp = 6.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(8.dp)
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = spacing / 2)
                    .size(dotSize)
                    .background(
                        color = if (index == selectedIndex) activeColor else inactiveColor,
                        shape = CircleShape
                    )
            )
        }
    }
}
