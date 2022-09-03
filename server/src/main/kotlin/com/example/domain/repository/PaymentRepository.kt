package com.example.domain.repository

import com.example.domain.entity.Payment
import com.example.domain.entity.Payments

interface PaymentRepository {
    // yearMonth: ex. 202209
    fun get(yearMonth: String = ""): Payments
    fun create(payment: Payment)
    fun delete(id: Int)
}
