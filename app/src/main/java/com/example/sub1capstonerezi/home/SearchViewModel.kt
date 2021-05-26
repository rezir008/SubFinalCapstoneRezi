package com.example.sub1capstonerezi.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.DataMTUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(private val useCase: DataMTUseCase) : ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        queryChannel.offer(search)
    }

    val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            useCase.getMovieSearch(it)
        }.asLiveData()

    val tvResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            useCase.getTvSearch(it)
        }.asLiveData()
}