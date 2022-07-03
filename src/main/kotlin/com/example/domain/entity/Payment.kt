package com.example.domain.entity

import java.time.ZonedDateTime

data class Payment(
    val id: Int,
    val description: String,
    val amount: Int,
    val paidAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)

data class Payments(
    val list: List<Payment>
) : List<Payment> by list
