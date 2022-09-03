package com.example.infrastructure.repository

import com.example.domain.entity.Payment
import com.example.domain.entity.Payments
import com.example.domain.repository.PaymentRepository
import java.sql.Connection
import java.sql.PreparedStatement
import java.time.ZonedDateTime

class PaymentRepositoryImpl(
    private val connection: Connection
) : PaymentRepository {

    override fun get(yearMonth: String): Payments {
        val statement: PreparedStatement
        var query = "SELECT * FROM payments"
        if (yearMonth.isNotEmpty()) {
            query += " WHERE DATE_FORMAT(paid_at, '%Y%m') = ?;"
            statement = connection.prepareStatement(query)
            statement.setString(1, yearMonth)
        } else {
            query += ";"
            statement = connection.prepareStatement(query)
        }

        val rs = statement.executeQuery()

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
