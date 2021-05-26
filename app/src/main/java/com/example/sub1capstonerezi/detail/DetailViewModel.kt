package com.example.sub1capstonerezi.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.DataMT
import com.example.core.domain.usecase.DataMTUseCase

class DetailViewModel (private val useCase: DataMTUseCase) : ViewModel() {
    fun setDataMTFav(data: DataMT, newStatus: Boolean) = useCase.setDataMTFav(data, newStatus)
}