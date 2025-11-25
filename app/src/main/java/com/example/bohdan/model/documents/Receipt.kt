package com.example.bohdan
import java.util.Date

class Receipt(
    id: Int,
    date: Date,
    totalAmount: Double,
    sender: String,
    receiver: String,
    val paymentMethod: String,
    val transactionId: String
) : Document(id, sender, receiver, totalAmount, date) {
    override fun toString(): String {
        return "Receipt(id=$id, total=$totalAmount, method='$paymentMethod', txId='$transactionId')"
    }
}