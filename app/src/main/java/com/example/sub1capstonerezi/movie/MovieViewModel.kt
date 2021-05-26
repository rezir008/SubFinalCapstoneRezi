package com.example.sub1capstonerezi.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.Resource
import com.example.core.domain.model.DataMT
import com.example.core.domain.usecase.DataMTUseCase

class MovieViewModel(private val useCase: DataMTUseCase) : ViewModel() {
    fun getMovie(): LiveData<Resource<List<DataMT>>> = useCase.getMovie().asLiveData()
}