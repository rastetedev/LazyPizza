package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raulastete.lazypizza.domain.entity.Product
import com.raulastete.lazypizza.presentation.ui.model.GenericProductCardUi
import com.raulastete.lazypizza.presentation.ui.model.MenuCardUi
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi

@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    menuCardUi: MenuCardUi,
    navigateToPizzaDetail: (Product) -> Unit,
    addGenericProductToCard: (Product) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String, Int) -> Unit,
    decreaseGenericProductCount: (String, Int) -> Unit
) {
    when (menuCardUi) {
        is PizzaCardUi -> PizzaCard(
            modifier = modifier,
            pizzaCardUi = menuCardUi
        ) {
            navigateToPizzaDetail(menuCardUi.product)
        }

        is GenericProductCardUi -> GenericProductCard(
            modifier = modifier,
            product = menuCardUi.product,
            onClickAddToCart = {
                addGenericProductToCard(menuCardUi.product)
            },
            onClickDecreaseCount = {
                decreaseGenericProductCount(menuCardUi.product.id, menuCardUi.count)
            },
            onClickIncreaseCount = {
                increaseGenericProductCount(menuCardUi.product.id, menuCardUi.count)
            },
            onClickRemoveFromCart = {
                removeGenericProductFromCard(menuCardUi.product.id)
            },
            count = menuCardUi.count,
            totalPrice = menuCardUi.totalPrice
        )
    }
}