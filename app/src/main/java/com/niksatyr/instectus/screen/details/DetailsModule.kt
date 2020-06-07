package com.niksatyr.instectus.screen.details

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    viewModel { DetailsViewModel(get()) }
}