package com.example.application.view

import com.example.SystemZonedDateTime
import com.example.domain.entity.Payments
import java.time.OffsetDateTime

data class PaymentView(
    val id: Int,
    val description: String,
    val amount: Int,
    val paidAt: OffsetDateTime,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class PaymentsView(
    val list: List<PaymentView>
) : List<PaymentView> by list

fun newPaymentsView(payments: Payments, systemDateTime: SystemZonedDateTime): PaymentsView {
    return PaymentsView(
        payments.map { payment ->
            PaymentView(
                id = payment.id!!,
                description = payment.description,
                amount = payment.amount,
                paidAt = payment.paidAt.withZoneSameInstant(systemDateTime.toZoneId())
                    .toOffsetDateTime(),
                createdAt = payment.createdAt.withZoneSameInstant(systemDateTime.toZoneId())
                    .toOffsetDateTime(),
                updatedAt = payment.updatedAt.withZoneSameInstant(systemDateTime.toZoneId())
                    .toOffsetDateTime(),
            )
        }
    )
}
