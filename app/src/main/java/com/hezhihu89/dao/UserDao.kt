package com.hezhihu89.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.hezhihu89.entity.User

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:06
 * 功能描述:
 */
@Dao
interface UserDao: BaseDao<User>{

    @Query("select * from User")
    fun getAllUser():LiveData<MutableList<User>>

    @Query("select * from User where userID = :studentID")
    fun getUser(studentID:Int):User

    @Query("select * from User order by userID asc ")
    fun getAllByDateAsc():LiveData<MutableList<User>>

    @Query("delete from User")
    fun deleteAll()

    /**
     * 根据ID 删除User
     * @param userId 用户ID
     */
    @Query("delete from User where userID = :userId")
    fun delete(userId: Long)

}