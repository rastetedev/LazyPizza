package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raulastete.lazypizza.presentation.ui.components.product.GeneralProductCard
import com.raulastete.lazypizza.presentation.ui.components.product.PizzaCard
import com.raulastete.lazypizza.presentation.ui.model.ProductCard

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: ProductCard,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit
) {
    when (product) {
        is ProductCard.PizzaCard -> PizzaCard(
            modifier = modifier,
            pizzaUi = product
        ) {
            navigateToPizzaDetail(product.id)
        }

        is ProductCard.GenericProductCard -> GeneralProductCard(
            modifier = modifier,
            genericProductCard = product,
            onClickAddToCart = {
                addGenericProductToCard(product.id)
            },
            onClickDecreaseCount = {
                decreaseGenericProductCount(product.id)
            },
            onClickIncreaseCount = {
                increaseGenericProductCount(product.id)
            },
            onClickRemoveFromCart = {
                removeGenericProductFromCard(product.id)
            }
        )

        else -> throw Exception("Invalid product type")
    }
}
