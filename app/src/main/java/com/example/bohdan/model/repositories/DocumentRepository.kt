package com.example.bohdan

import android.content.Context
import com.example.bohdan.Document
import com.example.bohdan.model.database.AppDatabase
import com.example.bohdan.model.database.toDomain
import com.example.bohdan.model.database.toEntity
import com.example.bohdan.Repository

class DocumentRepository(context: Context) : Repository<Document> {

    private val dao = AppDatabase.getDatabase(context).documentDao()

    override fun add(item: Document): Boolean {
        dao.insert(item.toEntity())
        return true
    }

    override fun getById(id: Int): Document? {
        return dao.getById(id)?.toDomain()
    }

    override fun remove(id: Int): Boolean {
        dao.deleteById(id)
        return true
    }

    override fun listAll(): List<Document> {
        return dao.getAllDocuments().map { it.toDomain() }
    }
}