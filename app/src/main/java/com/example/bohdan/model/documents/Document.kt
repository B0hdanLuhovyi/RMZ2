package com.example.bohdan
import java.util.Date

// Базовий клас для всіх документів
sealed class Document(
    val id: Int,
    val sender: String,
    val receiver: String,
    val totalAmount: Double,
    val date: Date = Date()
){
    override fun toString(): String {
        return "Document(id=$id, date=$date, total=$totalAmount, sender='$sender', receiver='$receiver')"
    }
}
