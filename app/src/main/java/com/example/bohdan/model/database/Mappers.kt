package com.example.bohdan.model.database

import com.example.bohdan.Invoice
import com.example.bohdan.Receipt
import com.example.bohdan.Waybill
import com.example.bohdan.Document
import java.util.Date


fun Document.toEntity(): DocumentEntity {
    return when (this) {
        is Invoice -> DocumentEntity(
            id = if (this.id > 0) this.id else 0,
            docType = "INVOICE",
            sender = sender, receiver = receiver, totalAmount = totalAmount, date = date.time,
            paymentDue = paymentDue.time, isPaid = isPaid
        )
        is Receipt -> DocumentEntity(
            id = if (this.id > 0) this.id else 0,
            docType = "RECEIPT",
            sender = sender, receiver = receiver, totalAmount = totalAmount, date = date.time,
            paymentMethod = paymentMethod, transactionId = transactionId
        )
        is Waybill -> DocumentEntity(
            id = if (this.id > 0) this.id else 0,
            docType = "WAYBILL",
            sender = sender, receiver = receiver, totalAmount = totalAmount, date = date.time,
            carrier = carrier, vehicleNumber = vehicleNumber,
            cargoList = cargoList.joinToString(",")
        )
    }
}


fun DocumentEntity.toDomain(): Document {
    return when (docType) {
        "INVOICE" -> Invoice(
            id = id, sender = sender, receiver = receiver, totalAmount = totalAmount, date = Date(date),
            paymentDue = Date(paymentDue ?: 0), isPaid = isPaid ?: false
        )
        "RECEIPT" -> Receipt(
            id = id, sender = sender, receiver = receiver, totalAmount = totalAmount, date = Date(date),
            paymentMethod = paymentMethod ?: "", transactionId = transactionId ?: ""
        )
        "WAYBILL" -> Waybill(
            id = id, sender = sender, receiver = receiver, totalAmount = totalAmount, date = Date(date),
            carrier = carrier ?: "", vehicleNumber = vehicleNumber ?: "",
            cargoList = cargoList?.split(",") ?: emptyList()
        )
        else -> throw IllegalArgumentException("Unknown document type")
    }
}