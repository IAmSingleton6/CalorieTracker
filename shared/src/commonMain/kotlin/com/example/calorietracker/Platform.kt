package com.example.calorietracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform