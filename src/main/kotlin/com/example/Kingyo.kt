package com.example

import com.example.application.handler.UserHandler
import com.example.domain.repository.PaymentRepository
import com.example.infrastructure.repository.PaymentRepositoryImpl
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

class Router(
    private val paymentRepository: PaymentRepository
) {
    val handler: HttpHandler
        get() {
            return routes(
                "/payments" bind GET to UserHandler(paymentRepository).getPayments(),
            )
        }
}

fun main() {
    val paymentRepository = PaymentRepositoryImpl()

    val router = Router(
        paymentRepository
    )

    val server = router.handler.asServer(SunHttp(9000)).start()
    println("Server started on " + server.port())
}
