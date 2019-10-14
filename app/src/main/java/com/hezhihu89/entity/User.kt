package com.hezhihu89.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import com.hezhihu89.kt.TypeBean

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 16:52
 * 功能描述:
 */
@Entity(tableName = "User",primaryKeys = ["userID"])
data class User  (
        var userID: Long = 1,
        var userName: String = "",
        var firstName: String = "",
        var lastName: String = "",
        @Embedded
    var address: Address = Address("",10086),
        @Ignore
    var type: Int = 0
): TypeBean {

    companion object{
        fun type(type: Int): User{
            return User(type = type)
        }
    }

    override fun type(): Int {
        return type
    }
}

data class Address(
        var city: String = "",
        var postNumber: Int = 10086
)