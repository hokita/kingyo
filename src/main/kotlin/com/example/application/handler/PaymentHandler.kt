package com.example.application.handler

import com.example.domain.entity.Payments
import com.example.domain.repository.PaymentRepository
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto

class PaymentHandler(
    private val paymentRepository: PaymentRepository
) {
    fun getPayments() = { _: Request ->
        val paymentLens = Body.auto<Payments>().toLens()
        val payments = paymentRepository.get()
        Response(OK).with(paymentLens of payments)
    }
}
