package com.raulastete.lazypizza.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [OrderItemDto::class])
abstract class LazyPizzaDatabase : RoomDatabase() {

    abstract fun orderItemDao(): OrderItemDao
}