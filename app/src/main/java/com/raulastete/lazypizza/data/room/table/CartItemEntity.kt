package com.raulastete.lazypizza.data.room.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.Category
import com.raulastete.lazypizza.domain.Extra
import com.raulastete.lazypizza.domain.Product
import kotlinx.serialization.Serializable
import kotlin.collections.mapKeys

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val name: String,
    val image: String,
    val unitPrice: Double,
    val quantity: Int,
    val extras: Map<ExtraEntity, Int>? = null,
    val totalPrice: Double
)

@Serializable
data class ExtraEntity(
    val unitPrice: Double,
    val name: String
)

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        productId = product.id,
        name = product.name,
        image = product.imageUrl,
        unitPrice = product.unitPrice.toDouble(),
        quantity = quantity,
        extras = extras?.toEntity(),
        totalPrice = totalPrice.toDouble()
    )
}

fun Map<Extra, Int>.toEntity(): Map<ExtraEntity, Int> {
    return mapKeys { (topping, _) ->
        ExtraEntity(
            unitPrice = topping.unitPrice.toDouble(),
            name = topping.name
        )
    }
}

fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        id = id,
        product = Product(
            id = productId,
            name = name,
            imageUrl = image,
            unitPrice = unitPrice.toBigDecimal(),
            description = null,
            category = Category("", "", 0) // Placeholder
        ),
        quantity = quantity,
        extras = extras?.toDomain()
    )
}

fun Map<ExtraEntity, Int>.toDomain(): Map<Extra, Int> {
    return mapKeys { (topping, _) ->
        Extra(
            unitPrice = topping.unitPrice.toBigDecimal(),
            name = topping.name
        )
    }
}