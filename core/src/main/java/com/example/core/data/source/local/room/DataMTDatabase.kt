package com.example.core.data.source.local.room

import androidx.room.Database
import com.example.core.data.source.local.entity.DataMTEntity
import androidx.room.RoomDatabase

@Database(entities = [DataMTEntity::class], version = 1, exportSchema = false)
abstract class DataMTDatabase : RoomDatabase() {
    abstract fun dataMTDao(): DataMTDao
}
