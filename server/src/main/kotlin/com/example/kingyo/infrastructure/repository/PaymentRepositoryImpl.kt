package com.example.kingyo.infrastructure.repository

import com.example.kingyo.domain.entity.Payment
import com.example.kingyo.domain.repository.PaymentRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.ZonedDateTime

@Repository
class PaymentRepositoryImpl(
    val client: DatabaseClient
) : PaymentRepository {
    override fun get(yearMonth: String): Flux<Payment> {
        var query = "SELECT * FROM payments"
        val spec: GenericExecuteSpec
        if (yearMonth.isNotEmpty()) {
            query += " WHERE DATE_FORMAT(paid_at, '%Y%m') = :yearMonth;"
            spec = client.sql(query).bind("yearMonth", yearMonth)
        } else {
            query += ";"
            spec = client.sql(query)
        }
        return spec.map { row ->
            val id = row.get("id", Number::class.java)!!
            val description = row.get("description", String::class.java)!!
            val amount = row.get("amount", Number::class.java)!!
            val paidAt = row.get("paid_at", ZonedDateTime::class.java)!!
            val createAt = row.get("created_at", ZonedDateTime::class.java)!!
            val updatedAt = row.get("updated_at", ZonedDateTime::class.java)!!

            Payment(
                id = id.toInt(),
                description = description,
                amount = amount.toInt(),
                paidAt = paidAt,
                createdAt = createAt,
                updatedAt = updatedAt
            )
        }.all()
    }

    override suspend fun create(payment: Payment) {
        val query = """
            INSERT INTO payments (description, amount, paid_at, created_at, updated_at)
             VALUE (:description, :amount, :paid_at, :created_at, :updated_at);
        """"
        client.sql(query)
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("description", payment.description)
            .bind("amount", payment.amount)
            .bind("paid_at", payment.paidAt)
            .bind("created_at", ZonedDateTime.now())
            .bind("updated_at", ZonedDateTime.now())
            .fetch()
            .first()
            .awaitFirst()
    }

    override suspend fun delete(id: Int) {
        val query = "DELETE from payments where id = :id;"
        client.sql(query)
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("id", id)
            .fetch()
            .rowsUpdated()
            .awaitFirst()
    }
}
