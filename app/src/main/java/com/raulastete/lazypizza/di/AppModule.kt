package com.raulastete.lazypizza.di

import com.raulastete.lazypizza.data.ProductFirebaseRepository
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    single<ProductRepository> {
        ProductFirebaseRepository()
    }
}