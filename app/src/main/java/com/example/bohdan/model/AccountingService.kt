package com.example.bohdan

import android.content.Context
import java.util.*

class AccountingService(context: Context) {

    private val repository = DocumentRepository(context)


    fun createInvoice(sender: String, receiver: String, total: Double, dueDate: Date): Invoice {
        val invoice = Invoice(0, Date(), total, sender, receiver, dueDate)
        repository.add(invoice)
        return invoice
    }

    fun createReceipt(sender: String, receiver: String, total: Double, method: String): Receipt {
        val txId = UUID.randomUUID().toString()
        val receipt = Receipt(0, Date(), total, sender, receiver, method, txId)
        repository.add(receipt)
        return receipt
    }

    fun createWaybill(sender: String, receiver: String, cargo: List<String>, total: Double): Waybill {
        val waybill = Waybill(0, Date(), total, sender, receiver, "LogiTrans", "AA1234BB", cargo)
        repository.add(waybill)
        return waybill
    }

    fun findDocumentById(id: Int): Document? = repository.getById(id)

    fun listAllDocuments(): List<Document> = repository.listAll()

    fun calculateTotalAmount(): Double = repository.listAll().sumOf { it.totalAmount }
}