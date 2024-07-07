package com.example.myTrip.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.myTrip.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Upsert
    suspend fun upsert(user: User): Long

    @Delete
    fun delete(user: User)

    @Query("select * from user u order by u.username")
    fun getAll(): Flow<List<User>>

    @Query("select * from user u where u.id = :id")
    suspend fun findById(id: Long): User?

}