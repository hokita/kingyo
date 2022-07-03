package com.example.infrastructure.repository

import com.example.domain.entity.Payment
import com.example.domain.entity.Payments
import com.example.domain.repository.PaymentRepository
import java.sql.Connection
import java.time.ZonedDateTime

class PaymentRepositoryImpl(
    connection: Connection
) : PaymentRepository {
    private val statement = connection.createStatement()

    override fun get(): Payments {
        val rs = statement.executeQuery("SELECT * FROM payments;")

        val payments = mutableListOf<Payment>()
        while (rs.next()) {
            val id = rs.getInt("id")
            val description = rs.getString("description")
            val amount = rs.getInt("amount")
            val paidAt = rs.getObject("paid_at", ZonedDateTime::class.java)
            val createAt = rs.getObject("created_at", ZonedDateTime::class.java)
            val updatedAt = rs.getObject("updated_at", ZonedDateTime::class.java)

            val payment = Payment(
                id = id,
                description = description,
                amount = amount,
                paidAt = paidAt,
                createdAt = createAt,
                updatedAt = updatedAt,
            )

            payments.add(payment)
        }

        return Payments(payments)
    }
}
