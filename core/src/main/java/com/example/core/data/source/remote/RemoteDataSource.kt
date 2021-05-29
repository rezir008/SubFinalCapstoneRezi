package com.example.core.data.source.remote

import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.TvResponse
import com.example.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.core.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import retrofit2.await

class RemoteDataSource(private val apiService: ApiService) {
    

    suspend fun getMovie(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovie().await()
                val listData = response.results
                if (listData != null) {
                    if (listData.isNotEmpty()) {
                        emit(ApiResponse.Success(response.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTv(): Flow<ApiResponse<List<TvResponse>>> {
        return flow {
            try {
                val response = apiService.getTv().await()
                val listData = response.results
                if (listData != null) {
                    if (listData.isNotEmpty()) {
                        emit(ApiResponse.Success(response.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}

