package com.raulastete.lazypizza.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raulastete.lazypizza.data.room.table.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items ORDER BY addedAt ASC")
    fun getAllItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItemEntity)

    @Update
    suspend fun updateItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE localId = :itemId")
    suspend fun deleteItem(itemId: String)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteItemFromProduct(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
