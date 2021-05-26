package com.example.favorite.di

import com.example.favorite.FavViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel {
        FavViewModel(get())
    }
}