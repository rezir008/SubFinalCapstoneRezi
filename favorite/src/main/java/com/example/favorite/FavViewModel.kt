package com.example.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.DataMT
import com.example.core.domain.usecase.DataMTUseCase

class FavViewModel (private val useCase: DataMTUseCase) : ViewModel() {
    fun getMovieFav(): LiveData<List<DataMT>> =
        useCase.getMovieFav().asLiveData()

    fun getTvFav(): LiveData<List<DataMT>> =
        useCase.getTvFav().asLiveData()

    fun setFavorite(dataMT: DataMT, newState: Boolean) {
        useCase.setDataMTFav(dataMT, newState)
    }
}