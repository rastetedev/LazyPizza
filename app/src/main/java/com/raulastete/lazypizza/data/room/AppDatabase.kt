package com.raulastete.lazypizza.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raulastete.lazypizza.data.room.converter.ExtraConverter
import com.raulastete.lazypizza.data.room.dao.CartDao
import com.raulastete.lazypizza.data.room.table.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
@TypeConverters(ExtraConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
