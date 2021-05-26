package com.example.sub1capstonerezi.di

import com.example.core.domain.usecase.DataMTInteractor
import com.example.core.domain.usecase.DataMTUseCase
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import com.example.sub1capstonerezi.tv.TvViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import com.example.sub1capstonerezi.detail.DetailViewModel
import com.example.sub1capstonerezi.home.SearchViewModel
import com.example.sub1capstonerezi.movie.MovieViewModel

val useCaseModule = module {
    factory<DataMTUseCase> { DataMTInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}