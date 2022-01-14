package com.example.wasan.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wasan.Model.TvShow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tv_shows" )
    fun getAllShows(): LiveData<List<TvShow>>

    @Insert
    fun insertShow(tvShow: TvShow)

    @Delete
    fun deleteShow(tvShow: TvShow)
}