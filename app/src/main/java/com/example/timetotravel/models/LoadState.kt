package com.example.timetotravel.models

data class LoadState (
    val loading: Boolean = false,
    val error: Boolean = false,
    val idle: Boolean = false
        )