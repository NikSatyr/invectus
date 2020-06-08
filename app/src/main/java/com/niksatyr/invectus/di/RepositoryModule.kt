package com.niksatyr.invectus.di

import com.niksatyr.invectus.repo.InstagramRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { InstagramRepository(get(), get()) }
}