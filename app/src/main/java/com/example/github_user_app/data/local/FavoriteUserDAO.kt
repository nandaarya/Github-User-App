package com.example.github_user_app.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUser: FavoriteUser)

    @Update
    suspend fun update(favoriteUser: FavoriteUser)

    @Delete
    suspend fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from FavoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUser WHERE FavoriteUser.username = :username)")
    fun isFavorite(username: String): LiveData<Boolean>
}