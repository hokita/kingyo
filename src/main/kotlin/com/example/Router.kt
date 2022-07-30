package com.example

import com.example.application.handler.PaymentHandler
import com.example.domain.repository.PaymentRepository
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes

class Router(
    private val systemDateTime: SystemZonedDateTime,
    private val paymentRepository: PaymentRepository
) {
    val handler: HttpHandler
        get() {
            val paymentHandler = PaymentHandler(systemDateTime, paymentRepository)

            return ServerFilters.CatchLensFailure.then(
                routes(
                    "/payments" bind Method.GET to paymentHandler.getAll,
                    "/payments" bind Method.POST to paymentHandler.create,
                )
            )
        }
}
