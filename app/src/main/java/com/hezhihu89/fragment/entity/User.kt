package com.hezhihu89.fragment.entity

import androidx.room.Entity

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 16:52
 * 功能描述:
 */
@Entity(tableName = "User",primaryKeys = ["userID","userName"])
data class User (
    var userID: Long = 1,
    var userName: String,
    var firstName: String,
    var lastName: String
)