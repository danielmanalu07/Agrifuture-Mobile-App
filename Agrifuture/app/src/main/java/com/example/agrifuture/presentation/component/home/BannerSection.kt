package com.example.agrifuture.presentation.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Banner
import com.example.agrifuture.presentation.repository.BannerRepository
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.launch

@Composable
fun BannerSection(banners: List<Banner>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = banners.size,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val banner = banners[page]

            Image(
                painter = painterResource(id = banner.image),
                contentDescription = banner.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
                .size(40.dp)
                .shadow(4.dp, CircleShape)
                .background(color = colorResource(id = R.color.white), CircleShape)
                .clickable {
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous",
                tint = colorResource(id = R.color.black),
                modifier = Modifier.size(25.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .size(40.dp)
                .shadow(4.dp, CircleShape)
                .background(color = colorResource(id = R.color.white), CircleShape)
                .clickable {
                    coroutineScope.launch {
                        if (pagerState.currentPage < banners.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next",
                tint = colorResource(id = R.color.black),
                modifier = Modifier.size(24.dp)
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            activeColor = colorResource(id = R.color.indicator_selected),
            inactiveColor = colorResource(id = R.color.gray).copy(alpha = 0.5f),
            indicatorWidth = 16.dp,
            indicatorHeight = 6.dp,
            spacing = 8.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBannerSection() {
    val bannerRepository = BannerRepository()
    BannerSection(bannerRepository.banners)
}




