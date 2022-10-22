package com.example.kingyo.domain.entity

import java.time.ZonedDateTime

class Payment(
    val id: Int? = null,
    val description: String,
    val amount: Int,
    val paidAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
