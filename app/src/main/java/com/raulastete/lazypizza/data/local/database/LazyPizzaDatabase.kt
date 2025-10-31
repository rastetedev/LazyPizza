package com.raulastete.lazypizza.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raulastete.lazypizza.data.local.dao.OrderItemDao
import com.raulastete.lazypizza.data.local.dao.OrderItemToppingDao
import com.raulastete.lazypizza.data.local.dto.OrderItemDto
import com.raulastete.lazypizza.data.local.dto.OrderItemToppingDto

@Database(version = 1, entities = [OrderItemDto::class, OrderItemToppingDto::class])
abstract class LazyPizzaDatabase : RoomDatabase() {

    abstract fun orderItemDao(): OrderItemDao

    abstract fun orderItemToppingDao(): OrderItemToppingDao
}