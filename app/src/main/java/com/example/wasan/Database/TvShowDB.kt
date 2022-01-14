package com.example.wasan.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wasan.Dao.TvShowDao
import com.example.wasan.Model.TvShow


@Database(
    entities = [TvShow::class], version = 2
)

abstract class TvShowDB:RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao

    companion object{
        var instance: TvShowDB? = null
        fun getInstance ( context: Context): TvShowDB {

            if (instance != null)
                return instance as TvShowDB
            instance = Room.databaseBuilder(context, TvShowDB::class.java,"TvShows")
                .fallbackToDestructiveMigration()
                .build()
            return instance as TvShowDB
        }
    }
}