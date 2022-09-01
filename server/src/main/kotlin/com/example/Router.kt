package com.example

import com.example.application.handler.PaymentHandler
import com.example.domain.repository.PaymentRepository
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.events.AutoMarshallingEvents
import org.http4k.events.Event
import org.http4k.events.EventFilters
import org.http4k.events.then
import org.http4k.filter.AllowAllOriginPolicy
import org.http4k.filter.CorsPolicy
import org.http4k.filter.ResponseFilters
import org.http4k.filter.ServerFilters
import org.http4k.format.Jackson
import org.http4k.routing.bind
import org.http4k.routing.routes

class Router(
    private val systemDateTime: SystemZonedDateTime,
    private val paymentRepository: PaymentRepository
) {
    val handler: HttpHandler
        get() {
            val events =
                EventFilters.AddTimestamp()
                    .then(EventFilters.AddEventName())
                    .then(EventFilters.AddZipkinTraces())
                    .then(AutoMarshallingEvents(Jackson))

            val paymentHandler = PaymentHandler(systemDateTime, paymentRepository)

            return ServerFilters.Cors(
                CorsPolicy(
                    AllowAllOriginPolicy,
                    listOf("*"),
                    Method.values().toList(),
                    true
                )
            )
                .then(
                    ResponseFilters.ReportHttpTransaction {
                        events(
                            IncomingHttpRequest(
                                it.request.uri,
                                it.response.status.code,
                                it.duration.toMillis()
                            )
                        )
                    }.then(
                        Filter { next ->
                            { request ->
                                try {
                                    next(request)
                                } catch (e: Exception) {
                                    println("{\"error\":\"$e\"}")
                                    Response(Status.INTERNAL_SERVER_ERROR)
                                }
                            }
                        }.then(
                            routes(
                                "/payments" bind Method.GET to paymentHandler.getAll,
                                "/payments" bind Method.POST to paymentHandler.create,
                                "/payments/{id}" bind Method.DELETE to paymentHandler.delete
                            )
                        )
                    )
                )
        }

    data class IncomingHttpRequest(val uri: Uri, val status: Int, val duration: Long) : Event
}
