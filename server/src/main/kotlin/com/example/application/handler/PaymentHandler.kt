package com.example.application.handler

import com.example.SystemZonedDateTime
import com.example.application.form.PaymentCreateForm
import com.example.application.view.PaymentsView
import com.example.application.view.newPaymentsView
import com.example.domain.entity.Payment
import com.example.domain.repository.PaymentRepository
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.path
import java.time.format.DateTimeFormatter

class PaymentHandler(
    private val systemDateTime: SystemZonedDateTime,
    private val paymentRepository: PaymentRepository
) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")

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
                paidAt = form.paidAt.atZone(systemDateTime.toZoneId()),
                createdAt = systemDateTime.now(),
                updatedAt = systemDateTime.now()
            )
        )
        Response(CREATED)
    }

    val delete: HttpHandler = handler@{ request: Request ->
        println(request.path("id"))
        val id = request.path("id")?.toIntOrNull() ?: return@handler Response(NOT_FOUND)

        paymentRepository.delete(id)
        Response(OK)
    }
}
