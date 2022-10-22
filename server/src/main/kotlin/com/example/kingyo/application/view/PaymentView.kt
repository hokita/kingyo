package com.example.kingyo.application.view

import com.example.kingyo.domain.entity.Payment
import java.time.ZonedDateTime

data class PaymentView(
    val id: Int,
    val description: String,
    val amount: Int,
    val paidAt: ZonedDateTime,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun create(payment: Payment): PaymentView {
            return PaymentView(
                payment.id!!,
                payment.description,
                payment.amount,
                payment.paidAt,
                payment.createdAt,
                payment.updatedAt
            )
        }
    }
}
