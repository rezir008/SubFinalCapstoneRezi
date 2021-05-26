package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.DataMT
import kotlinx.coroutines.flow.Flow

interface IDataMTRepository {
    fun getMovie(): Flow<Resource<List<DataMT>>>

    fun getTv(): Flow<Resource<List<DataMT>>>

    fun getMovieFav(): Flow<List<DataMT>>

    fun getTvFav(): Flow<List<DataMT>>

    fun getMovieSearch(search: String): Flow<List<DataMT>>

    fun getTvSearch(search: String): Flow<List<DataMT>>

    fun setDataMTFav(dataMT: DataMT, state: Boolean)
}