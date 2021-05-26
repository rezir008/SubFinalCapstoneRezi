package com.example.core.data.source.local

import com.example.core.data.source.local.entity.DataMTEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import com.example.core.data.source.local.room.DataMTDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val dao: DataMTDao) {

    fun getMovie(): Flow<List<DataMTEntity>> {
        return dao.getMovie()
    }
    fun getTv(): Flow<List<DataMTEntity>> {
        return dao.getTv()
    }

    fun getMovieFav(): Flow<List<DataMTEntity>> {
        return dao.getMovieFav()
    }
    fun getTvFav(): Flow<List<DataMTEntity>> {
        return dao.getTvFav()
    }

    fun getMovieSearch(search: String): Flow<List<DataMTEntity>> {
        return dao.getMovieSearch(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    fun getTvSearch(search: String): Flow<List<DataMTEntity>> {
        return dao.getTvSearch(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    suspend fun insertDataMT(movie: List<DataMTEntity>) = dao.insertDataMT(movie)

    fun setDataMTFav(movie: DataMTEntity, newState: Boolean) {
        movie.favorite = newState
        dao.updateDataMTFavorite(movie)
    }
}