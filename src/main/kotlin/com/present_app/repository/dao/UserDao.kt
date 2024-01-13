package com.present_app.repository.dao

import com.present_app.repository.entity.User

interface UserDao : BaseDao<User, String> {

    fun getUserById(id: Int): User?
    fun updateName(name: String, userId: Int): Boolean
    fun updateIcon(icon: String, userId: Int): Boolean
}