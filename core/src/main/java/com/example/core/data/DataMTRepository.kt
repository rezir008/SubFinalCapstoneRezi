package com.example.core.data

import com.example.core.data.response.MovieResponse
import com.example.core.data.response.TvResponse
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.DataMT
import com.example.core.domain.repository.IDataMTRepository


class DataMTRepository(
    private val remoteData: RemoteDataSource,
    private val localData: LocalDataSource,
    private val appExe: AppExecutors
) : IDataMTRepository {

    override fun getMovie(): Flow<Resource<List<DataMT>>> =
        object : NetworkBoundResource<List<DataMT>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<DataMT>> {
                return localData.getMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<DataMT>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteData.getMovie()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localData.insertDataMT(movieList)
            }
        }.asFlow()

    override fun getTv(): Flow<Resource<List<DataMT>>> =
        object : NetworkBoundResource<List<DataMT>, List<TvResponse>>() {
            override fun loadFromDB(): Flow<List<DataMT>> {
                return localData.getTv().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<DataMT>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse>>> {
                return remoteData.getTv()
            }

            override suspend fun saveCallResult(data: List<TvResponse>) {
                val tvList = DataMapper.mapTvResponsesToEntities(data)
                localData.insertDataMT(tvList)
            }
        }.asFlow()

    override fun getMovieSearch(search: String): Flow<List<DataMT>> {
        return localData.getMovieSearch(search).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getTvSearch(search: String): Flow<List<DataMT>> {
        return localData.getTvSearch(search).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getMovieFav(): Flow<List<DataMT>> {
        return localData.getMovieFav().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getTvFav(): Flow<List<DataMT>> {
        return localData.getTvFav().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setDataMTFav(dataMT: DataMT, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(dataMT)
        appExe.diskIO().execute { localData.setDataMTFav(movieEntity, state) }
    }
}
