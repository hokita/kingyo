package com.example

import com.example.application.handler.PaymentHandler
import com.example.domain.repository.PaymentRepository
import com.example.infrastructure.repository.PaymentRepositoryImpl
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.exitProcess

class Router(
    private val paymentRepository: PaymentRepository
) {
    val handler: HttpHandler
        get() {
            return routes(
                "/payments" bind GET to PaymentHandler(paymentRepository).getPayments(),
            )
        }
}

fun main() {
    val connection: Connection
    try {
        connection = DriverManager.getConnection("jdbc:mysql://db/kingyo", "sa", "1234")
    } catch (e: Exception) {
        e.printStackTrace()
        System.err.println("Failed to connect to DB.")
        exitProcess(1)
    }

    val paymentRepository = PaymentRepositoryImpl(connection)
    val router = Router(
        paymentRepository
    )

    router.handler.asServer(SunHttp(9000)).start()
    println("Server started.")
}
