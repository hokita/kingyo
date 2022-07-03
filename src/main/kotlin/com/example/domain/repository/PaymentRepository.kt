package com.example.domain.repository

import com.example.domain.entity.Payments

interface PaymentRepository {
    fun get(): Payments
}
