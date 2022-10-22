package com.example.kingyo.application.controller

import com.example.kingyo.application.form.PaymentForm
import com.example.kingyo.application.view.PaymentView
import com.example.kingyo.domain.entity.Payment
import com.example.kingyo.domain.repository.PaymentRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.ZoneId
import java.time.ZonedDateTime

@RestController
@CrossOrigin
class PaymentController(
    val paymentRepository: PaymentRepository
) {
    @GetMapping("/payments")
    fun paymentGet(
        // FIXME: yearDate to yearMonth
        @RequestParam yearDate: String?
    ): Flux<PaymentView> {
        return paymentRepository.get(yearDate ?: "").map {
            PaymentView.create(it)
        }
    }

    @PostMapping("/payments")
    suspend fun paymentPost(
        @RequestBody form: PaymentForm
    ) {
        val payment = Payment(
            description = form.description,
            amount = form.amount,
            paidAt = form.paidAt.atZone(ZoneId.of("Asia/Tokyo")),
            createdAt = ZonedDateTime.now(),
            updatedAt = ZonedDateTime.now()
        )

        paymentRepository.create(payment)
    }

    @DeleteMapping("/payments/{id}")
    suspend fun paymentDelete(
        @PathVariable id: Int
    ) {
        paymentRepository.delete(id)
    }
}
