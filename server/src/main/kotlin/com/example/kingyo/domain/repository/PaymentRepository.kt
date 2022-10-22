package com.example.kingyo.domain.repository

import com.example.kingyo.domain.entity.Payment
import reactor.core.publisher.Flux

interface PaymentRepository {
    fun get(yearMonth: String): Flux<Payment>
    suspend fun create(payment: Payment)
    suspend fun delete(id: Int)
}
