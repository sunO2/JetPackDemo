package com.hezhihu89.fragment.dao

import androidx.room.Dao
import androidx.room.Query
import com.hezhihu89.fragment.entity.User

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:06
 * 功能描述:
 */
@Dao
interface UserDao: BaseDao<User>{

    @Query("select * from User")
    fun getAllUser():MutableList<User>

    @Query("select * from User where userID = :studentID")
    fun getUser(studentID:Int):User

    @Query("select * from User order by userID desc ")
    fun getAllByDateDesc():MutableList<User>

    @Query("delete from User")
    fun deleteAll()

}