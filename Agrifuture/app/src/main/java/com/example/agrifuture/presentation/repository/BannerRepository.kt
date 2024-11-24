package com.example.agrifuture.presentation.repository

import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Banner

class BannerRepository {
    val banners = listOf(
        Banner(image = R.drawable.carousel_1, "banner 1"),
        Banner(image = R.drawable.carousel_2, "banner 2"),
        Banner(image = R.drawable.carousel_3, "banner 3")
    )
}