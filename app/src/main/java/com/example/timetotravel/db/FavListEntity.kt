package com.example.timetotravel.db

import androidx.room.Entity

@Entity
data class FavListEntity(
    val favList: List<String>
)