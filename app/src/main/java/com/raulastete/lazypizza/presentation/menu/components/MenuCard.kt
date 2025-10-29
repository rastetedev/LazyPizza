package com.raulastete.lazypizza.presentation.menu.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raulastete.lazypizza.presentation.ui.components.product.GenericProductCard
import com.raulastete.lazypizza.presentation.ui.components.product.PizzaCard
import com.raulastete.lazypizza.presentation.ui.model.GenericProductCardUi
import com.raulastete.lazypizza.presentation.ui.model.MenuCardUi
import com.raulastete.lazypizza.presentation.ui.model.PizzaCardUi

@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    menuCardUi: MenuCardUi,
    navigateToPizzaDetail: (String) -> Unit,
    addGenericProductToCard: (String) -> Unit,
    removeGenericProductFromCard: (String) -> Unit,
    increaseGenericProductCount: (String) -> Unit,
    decreaseGenericProductCount: (String) -> Unit
) {
    when (menuCardUi) {
        is PizzaCardUi -> PizzaCard(
            modifier = modifier,
            pizzaCardUi = menuCardUi
        ) {
            navigateToPizzaDetail(menuCardUi.id)
        }

        is GenericProductCardUi -> GenericProductCard(
            modifier = modifier,
            genericProductCardUi = menuCardUi,
            onClickAddToCart = {
                addGenericProductToCard(menuCardUi.id)
            },
            onClickDecreaseCount = {
                decreaseGenericProductCount(menuCardUi.id)
            },
            onClickIncreaseCount = {
                increaseGenericProductCount(menuCardUi.id)
            },
            onClickRemoveFromCart = {
                removeGenericProductFromCard(menuCardUi.id)
            }
        )
    }
}