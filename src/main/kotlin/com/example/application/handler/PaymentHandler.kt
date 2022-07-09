package com.example.application.handler

import com.example.SystemZonedDateTime
import com.example.application.form.PaymentCreateForm
import com.example.application.view.PaymentsView
import com.example.application.view.newPaymentsView
import com.example.domain.entity.Payment
import com.example.domain.repository.PaymentRepository
import org.http4k.core.* // ktlint-disable no-wildcard-imports
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto

class PaymentHandler(
    private val systemDateTime: SystemZonedDateTime,
    private val paymentRepository: PaymentRepository
) {
    val getAll: HttpHandler = { _: Request ->
        val paymentLens = Body.auto<PaymentsView>().toLens()
        val payments = paymentRepository.getAll()
        val paymentsView = newPaymentsView(payments, systemDateTime)
        Response(OK).with(paymentLens of paymentsView)
    }

    val create: HttpHandler = { request: Request ->
        val formLens = Body.auto<PaymentCreateForm>().toLens()
        val form = formLens(request)
        paymentRepository.create(
            Payment(
                description = form.description,
                amount = form.amount,
                paidAt = systemDateTime.now(), // temp
                createdAt = systemDateTime.now(),
                updatedAt = systemDateTime.now(),
            )
        )
        Response(CREATED)
    }
}
