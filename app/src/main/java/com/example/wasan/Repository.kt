package com.example.wasan

import androidx.lifecycle.LiveData
import com.example.wasan.Dao.TvShowDao
import com.example.wasan.Model.TvShow

class Repository(private val tvShowsDao: TvShowDao) {
    val getShows: LiveData<List<TvShow>> = tvShowsDao.getAllShows()

    suspend fun add(tvShows: TvShow){
        tvShowsDao.insertShow(tvShows)
    }

    suspend fun delete(tvShows: TvShow){
        tvShowsDao.deleteShow(tvShows)
    }

}