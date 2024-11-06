package com.example.agrifuture.presentation.model

import androidx.annotation.DrawableRes
import com.example.agrifuture.R

sealed class Onboarding(
    @DrawableRes
    val image: Int,
    val description: String,
) {
    data object FirstPage: Onboarding(
        image = R.drawable.onboarding1,
        description = "Selamat datang! Temukan pupuk terbaik untuk tanaman Anda di sini. Beli pupuk dengan mudah dan buat lahan Anda lebih subur untuk hasil yang maksimal."
    )
    data object SecondPage : Onboarding(
        image = R.drawable.onboarding2,
        description = "Temukan pupuk terbaik untuk tanaman Anda dengan rekomendasi yang disesuaikan berdasarkan jenis tanah dan tanaman."
    )
    data object ThirdPage : Onboarding(
        image = R.drawable.onboarding3,
        description = "Pilih pupuk yang tepat dan beli dengan cepat. Tanamanmu akan tumbuh lebih sehat dan subur."
    )
}
