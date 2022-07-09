package com.example

import com.example.application.handler.PaymentHandler
import com.example.domain.repository.PaymentRepository
import com.example.infrastructure.repository.PaymentRepositoryImpl
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.sql.Connection
import java.sql.DriverManager
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.system.exitProcess

class SystemZonedDateTime(
    private val timeZone: TimeZone
) {
    fun now(): ZonedDateTime {
        return ZonedDateTime.now(timeZone.toZoneId())
    }

    fun toZoneId(): ZoneId {
        return timeZone.toZoneId()
    }
}

class Router(
    private val systemDateTime: SystemZonedDateTime,
    private val paymentRepository: PaymentRepository
) {
    val handler: HttpHandler
        get() {
            val paymentHandler = PaymentHandler(systemDateTime, paymentRepository)

            return ServerFilters.CatchLensFailure.then(
                routes(
                    "/payments" bind GET to paymentHandler.getAll,
                    "/payments" bind POST to paymentHandler.create,
                )
            )
        }
}

fun main() {
    val connection: Connection
    try {
        connection = DriverManager.getConnection(
            "jdbc:mysql://db/kingyo?useLegacyDatetimeCode=false",
            "sa",
            "1234"
        )
    } catch (e: Exception) {
        e.printStackTrace()
        System.err.println("Failed to connect to DB.")
        exitProcess(1)
    }

    val systemDateTime = SystemZonedDateTime(TimeZone.getTimeZone("Asia/Tokyo"))
    val paymentRepository = PaymentRepositoryImpl(connection)
    val router = Router(
        systemDateTime,
        paymentRepository
    )

    router.handler.asServer(SunHttp(9000)).start()
    println("Server started.")
}
