package com.example.wasan.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tv_shows")

data class TvShow (
    @PrimaryKey
    val id: Int,
    val url: String,
    val name: String,
    val genres: String,
    val language: String,
    val officialSite: String,
    val time: String,
    val days: String,
    val rating: String,
    val image: String,
    val premiered: String,
    val ended: String,
    val summary: String
)