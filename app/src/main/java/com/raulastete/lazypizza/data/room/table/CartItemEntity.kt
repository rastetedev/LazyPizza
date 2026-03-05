package com.raulastete.lazypizza.data.room.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raulastete.lazypizza.domain.CartItem
import com.raulastete.lazypizza.domain.Extra
import kotlinx.serialization.Serializable

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = false) val localId: String,
    val productId: String,
    val name: String,
    val image: String,
    val unitPrice: Double,
    val quantity: Int,
    val extras: Map<ExtraEntity, Int>? = null,
    val totalPrice: Double,
    val addedAt: Long
)

@Serializable
data class ExtraEntity(
    val unitPrice: Double,
    val name: String
)

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        localId = id,
        productId = productId,
        name = productName,
        image = productImageUrl,
        unitPrice = productUnitPrice.toDouble(),
        quantity = quantity,
        extras = extras?.toEntity(),
        totalPrice = totalPrice.toDouble(),
        addedAt = addedAt
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
        id = localId,
        productId = productId,
        productName = name,
        productImageUrl = image,
        productUnitPrice = unitPrice.toBigDecimal(),
        quantity = quantity,
        extras = extras?.toDomain(),
        addedAt = addedAt
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
