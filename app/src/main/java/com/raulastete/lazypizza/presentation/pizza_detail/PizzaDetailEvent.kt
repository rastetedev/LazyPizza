package com.raulastete.lazypizza.presentation.pizza_detail

sealed interface PizzaDetailEvent {
    object OnPizzaAddedToCart : PizzaDetailEvent
}