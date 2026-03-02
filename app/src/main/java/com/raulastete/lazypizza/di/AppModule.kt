package com.raulastete.lazypizza.di

import androidx.room.Room
import com.raulastete.lazypizza.data.repository.DefaultCartRepository
import com.raulastete.lazypizza.data.repository.DefaultProductRepository
import com.raulastete.lazypizza.data.room.AppDatabase
import com.raulastete.lazypizza.domain.CartRepository
import com.raulastete.lazypizza.domain.ProductRepository
import com.raulastete.lazypizza.presentation.cart.CartViewModel
import com.raulastete.lazypizza.presentation.home.HomeViewModel
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::PizzaDetailViewModel)
    viewModelOf(::CartViewModel)
    single<ProductRepository> {
        DefaultProductRepository()
    }
    single<CartRepository> {
        DefaultCartRepository(
            cartDao = get(),
            productRepository = get()
        )
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "app_database"
        ).build(
        )
    }
    single {
        get<AppDatabase>().cartDao()
    }
}