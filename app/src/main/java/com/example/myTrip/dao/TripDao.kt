package com.example.myTrip.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.myTrip.models.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Insert
    fun insert(trip: Trip): Long

    @Update
    fun update(trip: Trip)

    @Upsert
    suspend fun upsert(trip: Trip): Long

    @Delete
    suspend fun delete(trip: Trip)

    @Query("select * from trip p order by p.source")
    fun getAll(): Flow<List<Trip>>

    @Query("select * from trip p where p.id = :id")
    suspend fun findById(id: Long): Trip?

}