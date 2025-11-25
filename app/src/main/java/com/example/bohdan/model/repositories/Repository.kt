package com.example.bohdan

interface Repository<T> {
    fun add(item: T): Boolean
    fun getById(id: Int): T?
    fun remove(id: Int): Boolean
    fun listAll(): List<T>
}