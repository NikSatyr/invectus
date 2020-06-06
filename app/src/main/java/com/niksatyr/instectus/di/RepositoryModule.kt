package com.niksatyr.instectus.di

import com.niksatyr.instectus.repo.InstagramRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { InstagramRepository(get(), get()) }
}