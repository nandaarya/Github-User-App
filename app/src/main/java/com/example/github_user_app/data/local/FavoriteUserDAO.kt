package com.example.github_user_app.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from FavoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>
}