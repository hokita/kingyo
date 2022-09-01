package com.example.domain.repository

import com.example.domain.entity.Payment
import com.example.domain.entity.Payments

interface PaymentRepository {
    fun getAll(): Payments
    fun create(payment: Payment)
    fun delete(id: Int)
}
