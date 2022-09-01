package com.example.infrastructure.repository

import com.example.domain.entity.Payment
import com.example.domain.entity.Payments
import com.example.domain.repository.PaymentRepository
import java.sql.Connection
import java.time.ZonedDateTime

class PaymentRepositoryImpl(
    private val connection: Connection
) : PaymentRepository {

    override fun getAll(): Payments {
        val statement = connection.createStatement()
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
                updatedAt = updatedAt
            )

            payments.add(payment)
        }

        return Payments(payments)
    }

    override fun create(
        payment: Payment
    ) {
        val statement = connection.prepareStatement(
            "INSERT INTO payments (description, amount, paid_at, created_at, updated_at) VALUE (?, ?, ?, ?, ?);"
        )
        statement.setString(1, payment.description)
        statement.setInt(2, payment.amount)
        statement.setObject(3, payment.paidAt)
        statement.setObject(4, payment.createdAt)
        statement.setObject(5, payment.updatedAt)
        statement.executeUpdate()
    }

    override fun delete(
        id: Int
    ) {
        val statement = connection.prepareStatement(
            "DELETE from payments where id = ?;"
        )
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}
