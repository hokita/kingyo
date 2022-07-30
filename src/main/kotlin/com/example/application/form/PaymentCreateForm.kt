package com.example.application.form

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class PaymentCreateForm(
    val description: String,
    val amount: Int,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val paidAt: LocalDateTime
)
