package com.example.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.annotation.NonNull
import androidx.room.PrimaryKey

@Entity(tableName = "dataMT_Entity")
data class DataMTEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "titleDataMT")
    var title: String? = null,

    @ColumnInfo(name = "overviewDataMT")
    var overview: String? = null,

    @ColumnInfo(name = "scoreDataMT")
    var score: Double = 0.0,

    @ColumnInfo(name = "dateDataMT")
    var date: String? = null,

    @ColumnInfo(name = "posterDataMT")
    var poster: String? = null,

    @NonNull
    @ColumnInfo(name = "favoriteDataMT")
    var favorite: Boolean = false,

    @NonNull
    @ColumnInfo(name = "mt")
    var mt: Boolean = false
)