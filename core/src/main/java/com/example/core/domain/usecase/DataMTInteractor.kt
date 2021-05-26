package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.repository.IDataMTRepository
import kotlinx.coroutines.flow.Flow
import com.example.core.domain.model.DataMT

class DataMTInteractor (private val iRepository: IDataMTRepository) : DataMTUseCase {

    override fun getMovie(): Flow<Resource<List<DataMT>>> =
        iRepository.getMovie()

    override fun getTv(): Flow<Resource<List<DataMT>>> =
        iRepository.getTv()

    override fun getMovieFav(): Flow<List<DataMT>> =
        iRepository.getMovieFav()

    override fun getTvFav(): Flow<List<DataMT>> =
        iRepository.getTvFav()

    override fun getMovieSearch(search: String): Flow<List<DataMT>> =
        iRepository.getMovieSearch(search)

    override fun getTvSearch(search: String): Flow<List<DataMT>> =
        iRepository.getTvSearch(search)

    override fun setDataMTFav(dataMT: DataMT, state: Boolean) =
        iRepository.setDataMTFav(dataMT, state)
}