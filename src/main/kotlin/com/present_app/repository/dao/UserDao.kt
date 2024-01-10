package com.present_app.repository.dao

import com.present_app.repository.entity.User

interface UserDao : BaseDao<User, String> {
    fun updateItem(): Boolean
}