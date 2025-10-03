package com.raulastete.lazypizza.di

import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.raulastete.lazypizza.data.remote.MenuRemoteDataSource
import com.raulastete.lazypizza.data.repository.DefaultMenuRepository
import com.raulastete.lazypizza.domain.MenuRepository
import com.raulastete.lazypizza.presentation.pizza_detail.PizzaDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<FirebaseDatabase> {
        Firebase.database
    }
    single<MenuRemoteDataSource> {
        MenuRemoteDataSource(database = get())
    }
    single<MenuRepository> {
        DefaultMenuRepository(remoteDataSource = get())
    }
    viewModel { PizzaDetailViewModel(menuRepository = get()) }


}