package com.raulastete.lazypizza.di

import com.raulastete.lazypizza.data.ProductFirebaseRepository
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.cart.CartViewModel
import com.raulastete.lazypizza.presentation.home.HomeViewModel
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::PizzaDetailViewModel)
    viewModelOf(::CartViewModel)
    single<ProductRepository> {
        ProductFirebaseRepository()
    }
}