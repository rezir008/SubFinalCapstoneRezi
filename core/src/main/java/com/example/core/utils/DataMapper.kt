package com.example.core.utils

import com.example.core.data.response.MovieResponse
import com.example.core.domain.model.DataMT
import com.example.core.data.response.TvResponse
import com.example.core.data.source.local.entity.DataMTEntity

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<DataMTEntity> {
        val dataList = ArrayList<DataMTEntity>()
        input.map {
            with(it){
                    val dataMT = DataMTEntity(
                    id = id,
                    title = title,
                    overview = overview,
                    score = voteAverage,
                    date = releaseDate,
                    poster = posterPath,
                    favorite = false,
                    mt = false
                )
                dataList.add(dataMT)
            }
        }
        return dataList
    }

    fun mapTvResponsesToEntities(input: List<TvResponse>): List<DataMTEntity> {
        val dataList = ArrayList<DataMTEntity>()
        input.map {
            with(it){
                val dataMT = DataMTEntity(
                    id = id,
                    title = name,
                    overview = overview,
                    score = voteAverage,
                    date = firstAirDate,
                    poster = posterPath,
                    favorite = false,
                    mt = true
                )
                dataList.add(dataMT)
            }
        }
        return dataList
    }

    fun mapEntitiesToDomain(input: List<DataMTEntity>): List<DataMT> {
        return input.map {
            with(it){
                DataMT(
                    id = id,
                    title = title,
                    overview = overview,
                    score = score,
                    date = date,
                    poster = poster,
                    favorite = favorite,
                    mt = mt
                )
            }
        }
    }

    fun mapDomainToEntity(input: DataMT): DataMTEntity {
        return with(input) {
            DataMTEntity(
                id = id,
                title = title,
                overview = overview,
                score = score,
                date = date,
                poster = poster,
                favorite = favorite,
                mt = mt
            )
        }
    }
}