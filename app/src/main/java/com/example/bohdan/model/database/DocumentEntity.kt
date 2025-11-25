package com.example.bohdan.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val docType: String, // "INVOICE", "RECEIPT", "WAYBILL"
    val sender: String,
    val receiver: String,
    val totalAmount: Double,
    val date: Long,

    // Поля для Invoice
    val paymentDue: Long? = null,
    val isPaid: Boolean? = null,

    // Поля для Receipt
    val paymentMethod: String? = null,
    val transactionId: String? = null,

    // Поля для Waybill
    val carrier: String? = null,
    val vehicleNumber: String? = null,
    val cargoList: String? = null
)