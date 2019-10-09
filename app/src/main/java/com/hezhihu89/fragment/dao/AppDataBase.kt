package com.hezhihu89.fragment.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hezhihu89.fragment.APP
import com.hezhihu89.fragment.entity.User

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:09
 * 功能描述:
 */
@Database(entities = [User::class],version = 1)
abstract class AppDataBase: RoomDatabase(){

    abstract fun getUserDao(): UserDao

    companion object{
        val instance = Single.sin
    }

    private object Single{
        val sin: AppDataBase = Room.databaseBuilder(
            APP.instances,AppDataBase::class.java,"APP.db"
        ).allowMainThreadQueries().build()
    }
}