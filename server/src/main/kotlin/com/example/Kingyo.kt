package com.example

import com.example.infrastructure.repository.PaymentRepositoryImpl
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.sql.Connection
import java.sql.DriverManager
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.* // ktlint-disable no-wildcard-imports
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

fun main() {
    val connection: Connection
    try {
        connection = DriverManager.getConnection(
            "jdbc:mysql://db/kingyo?autoReconnect=true",
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
