package com.example.domain.entity

data class Payment(
    val description: String,
    val amount: Int
)

data class Payments(
    val list: List<Payment>
) : List<Payment> by list
