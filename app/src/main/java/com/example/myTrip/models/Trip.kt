package com.example.myTrip.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val source: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val budget: Double = 0.0,
    val type: TripType = TripType.LEISURE
) {
    fun isEmpty(): Boolean {
        return id == 0L && source.isEmpty() && startDate.isEmpty() && endDate.isEmpty() && budget == 0.0 && type == TripType.LEISURE;
    }
    fun hasEmpty(): Boolean {
        return source.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || budget == 0.0;
    }
}