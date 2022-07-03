package com.example.infrastructure.repository

import com.example.domain.entity.Payment
import com.example.domain.entity.Payments
import com.example.domain.repository.PaymentRepository

class PaymentRepositoryImpl : PaymentRepository {
    override fun get(): Payments {
        return Payments(
            listOf(Payment("test", 1000))
        )
    }
}
