package com.raulastete.lazypizza.data.room.converter

import androidx.room.TypeConverter
import com.raulastete.lazypizza.data.room.table.ExtraEntity
import kotlinx.serialization.json.Json

class ExtraConverter {

    private val json = Json {
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromExtras(value: Map<ExtraEntity, Int>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toExtras(value: String?): Map<ExtraEntity, Int>? {
        return value?.let { json.decodeFromString(it) }
    }
}