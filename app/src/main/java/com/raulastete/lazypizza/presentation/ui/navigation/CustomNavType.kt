package com.raulastete.lazypizza.presentation.ui.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.raulastete.lazypizza.domain.entity.Product
import kotlinx.serialization.json.Json

object CustomNavType {

    val ProductType = object : NavType<Product>(
        isNullableAllowed = false
    ) {
        override fun put(
            bundle: SavedState,
            key: String,
            value: Product
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(
            bundle: SavedState,
            key: String
        ): Product? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Product {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Product): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}