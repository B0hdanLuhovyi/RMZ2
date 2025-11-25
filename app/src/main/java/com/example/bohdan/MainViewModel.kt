package com.example.bohdan
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    private val accountingService = AccountingService()
    // Використовуємо mutableStateListOf для автоматичного оновлення UI при зміні списку
    private val _documents = mutableStateListOf<Document>()
    val documents: List<Document> get() = _documents
    var totalAmount by mutableStateOf(0.0)
        private set

    init {
        // Створюємо кілька прикладів документів при ініціалізації
        accountingService.createInvoice("Company A", "Client X", 1500.0, Date(System.currentTimeMillis() + 86400000 * 7))
        accountingService.createReceipt("Client Z", "Company A", 500.0, "Cash")
        accountingService.createWaybill("Warehouse 1", "Store 3", listOf("Goods Y"), 200.0)
        refreshDocuments()
    }

    fun createInvoice(sender: String, receiver: String, total: Double, dueDate: Date) {
        accountingService.createInvoice(sender, receiver, total, dueDate)
        refreshDocuments()
    }

    fun createReceipt(sender: String, receiver: String, total: Double, method: String) {
        accountingService.createReceipt(sender, receiver, total, method)
        refreshDocuments()
    }

    fun createWaybill(sender: String, receiver: String, cargo: List<String>, total: Double) {
        accountingService.createWaybill(sender, receiver, cargo, total)
        refreshDocuments()
    }

    fun findDocumentById(id: Int): Document? = accountingService.findDocumentById(id)

    private fun refreshDocuments() {
        _documents.clear()
        _documents.addAll(accountingService.listAllDocuments())
        totalAmount = accountingService.calculateTotalAmount()
    }
}
