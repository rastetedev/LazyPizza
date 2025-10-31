package com.raulastete.lazypizza.di

import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.raulastete.lazypizza.data.local.database.LazyPizzaDatabase
import com.raulastete.lazypizza.data.local.dao.OrderItemDao
import com.raulastete.lazypizza.data.local.dao.OrderItemToppingDao
import com.raulastete.lazypizza.data.remote.MenuRemoteDataSource
import com.raulastete.lazypizza.data.repository.DefaultCartRepository
import com.raulastete.lazypizza.data.repository.DefaultMenuRepository
import com.raulastete.lazypizza.domain.repository.CartRepository
import com.raulastete.lazypizza.domain.repository.MenuRepository
import com.raulastete.lazypizza.presentation.cart.CartViewModel
import com.raulastete.lazypizza.presentation.menu.MenuViewModel
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<FirebaseDatabase> {
        Firebase.database
    }
    single<LazyPizzaDatabase> {
        Room.databaseBuilder(
            context = androidApplication(),
            LazyPizzaDatabase::class.java,
            "lazy_pizza_db"
        ).build()
    }

    single<OrderItemDao> {
        get<LazyPizzaDatabase>().orderItemDao()
    }

    single<OrderItemToppingDao> {
        get<LazyPizzaDatabase>().orderItemToppingDao()
    }

    single<MenuRemoteDataSource> {
        MenuRemoteDataSource(database = get())
    }
    single<MenuRepository> {
        DefaultMenuRepository(remoteDataSource = get())
    }
    single<CartRepository> {
        DefaultCartRepository(
            orderItemDao = get(),
            orderItemToppingDao = get()
        )
    }
    viewModel { PizzaDetailViewModel(menuRepository = get(), cartRepository = get(), savedStateHandle = get()) }
    viewModel { MenuViewModel(menuRepository = get(), cartRepository = get()) }
    viewModel { CartViewModel(menuRepository = get(), cartRepository = get()) }
}