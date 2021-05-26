package com.example.core.data.source.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.core.data.source.local.entity.DataMTEntity
@Dao
interface DataMTDao {

    @Query("SELECT * FROM dataMT_Entity where mt = 0 ")
    fun getMovie(): Flow<List<DataMTEntity>>

    @Query("SELECT * FROM dataMT_Entity where mt = 1 ")
    fun getTv(): Flow<List<DataMTEntity>>

    @Query("SELECT * FROM dataMT_Entity where mt = 0 and favoriteDataMT = 1")
    fun getMovieFav(): Flow<List<DataMTEntity>>

    @Query("SELECT * FROM dataMT_Entity where mt = 1 and favoriteDataMT = 1")
    fun getTvFav(): Flow<List<DataMTEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataMT(dataMT: List<DataMTEntity>)

    @Update
    fun updateDataMTFavorite(dataMT: DataMTEntity)

    @Query("SELECT * FROM dataMT_Entity WHERE mt = 0 AND titleDataMT LIKE '%' || :search || '%'")
    fun getMovieSearch(search: String): Flow<List<DataMTEntity>>

    @Query("SELECT * FROM dataMT_Entity WHERE mt = 1 AND titleDataMT LIKE '%' || :search || '%'")
    fun getTvSearch(search: String): Flow<List<DataMTEntity>>
}