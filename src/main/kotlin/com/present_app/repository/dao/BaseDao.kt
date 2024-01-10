package com.present_app.repository.dao

interface BaseDao<T, E> {
    fun create(item: T): Boolean
    fun getItem(filter: E): T?
    fun deleteItem(filter: E): Boolean
}