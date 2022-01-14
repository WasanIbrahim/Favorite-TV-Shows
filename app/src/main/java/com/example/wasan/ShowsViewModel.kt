package com.example.wasan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.wasan.Database.TvShowDB
import com.example.wasan.Model.TvShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowsViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository
    private val tvShows: LiveData<List<TvShow>>

    init {
        val dao = TvShowDB.getInstance(application).tvShowDao()
        repository = Repository(dao)
        tvShows = repository.getShows
    }
    fun getTVShow(): LiveData<List<TvShow>> {
        return tvShows
    }

    fun deleteTVShow(tvShow: TvShow){
        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(tvShow)
        }
    }
}