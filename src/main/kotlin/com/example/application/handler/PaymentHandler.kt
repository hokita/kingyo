package com.example.application.handler

import com.example.application.form.PaymentCreateForm
import com.example.domain.entity.Payment
import com.example.domain.entity.Payments
import com.example.domain.repository.PaymentRepository
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import java.time.ZonedDateTime

class PaymentHandler(
    private val paymentRepository: PaymentRepository
) {
    fun getAll() = { _: Request ->
        val paymentLens = Body.auto<Payments>().toLens()
        val payments = paymentRepository.getAll()
        Response(OK).with(paymentLens of payments)
    }

    fun create() = { request: Request ->
        val formLens = Body.auto<PaymentCreateForm>().toLens()
        val form = formLens(request)
        paymentRepository.create(
            Payment(
                description = form.description,
                amount = form.amount,
                paidAt = ZonedDateTime.now(),
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
            )
        )
        Response(CREATED)
    }
}
