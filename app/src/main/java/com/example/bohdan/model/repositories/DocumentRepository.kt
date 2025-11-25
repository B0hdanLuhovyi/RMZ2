package com.example.bohdan

class DocumentRepository : Repository<Document> {
    private val documents = mutableListOf<Document>()

    override fun add(item: Document): Boolean {
        if (documents.any { it.id == item.id }) return false
        documents.add(item)
        return true
    }

    override fun getById(id: Int): Document? = documents.find { it.id == id }

    override fun remove(id: Int): Boolean = documents.removeIf { it.id == id }

    override fun listAll(): List<Document> = documents.toList()
}