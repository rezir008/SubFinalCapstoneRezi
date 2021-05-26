package com.example.sub1capstonerezi.tv

import androidx.lifecycle.asLiveData
import com.example.core.data.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.model.DataMT
import com.example.core.domain.usecase.DataMTUseCase

class TvViewModel(private val useCase: DataMTUseCase) : ViewModel() {
    fun getTv(): LiveData<Resource<List<DataMT>>> = useCase.getTv().asLiveData()
}