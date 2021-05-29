package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.ListResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.core.data.source.remote.response.MovieResponse
import retrofit2.Call
import com.example.core.data.source.remote.response.TvResponse
import com.example.core.utils.Network.API_KEY

interface ApiService {
    @GET("movie/now_playing")
    fun getMovie(
        @Query("api_key") apiKey: String = API_KEY
    ) : Call<ListResponse<MovieResponse>>

    @GET("tv/on_the_air")
    fun getTv(
        @Query("api_key") apiKey: String = API_KEY
    ) : Call<ListResponse<TvResponse>>
}
