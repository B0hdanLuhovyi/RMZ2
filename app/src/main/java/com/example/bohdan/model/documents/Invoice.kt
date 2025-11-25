package com.example.bohdan
import java.util.Date

class Invoice(
    id: Int,
    date: Date,
    totalAmount: Double,
    sender: String,
    receiver: String,
    val paymentDue: Date,
    var isPaid: Boolean = false
) : Document(id, sender, receiver, totalAmount, date) {
    fun markAsPaid() {
        isPaid = true
    }

    override fun toString(): String {
        return "Invoice(id=$id, total=$totalAmount, due=$paymentDue, paid=$isPaid)"
    }
}
