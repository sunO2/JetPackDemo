package com.hezhihu89.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hezhihu89.fragment.APP
import com.hezhihu89.entity.User
import com.hezhihu89.utils.newSingleThreadExecutor
import java.util.concurrent.ExecutorCompletionService

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:09
 * 功能描述:
 */
@Database(entities = [User::class],version = 3)
abstract class AppDataBase: RoomDatabase(){

    abstract fun getUserDao(): UserDao

    companion object{

        const val USER_TAB = "User"

        ///数据库 1升级到2
        val _v1Tov2Migration: Migration = object: Migration(1,2){

            val updateSql = arrayOf("alter table $USER_TAB add column city TEXT not null default 深圳",
                    "alter table $USER_TAB add column postNumber integer not null default 0")

            override fun migrate(database: SupportSQLiteDatabase) {
                updateSql.forEach {
                    database.execSQL(it)
                }
            }
        }
        val instance = Single.sin
    }



    private object Single{
        val sin: AppDataBase = Room.databaseBuilder(
            APP.instances,AppDataBase::class.java,"APP.db"
        ).addMigrations()
                .setQueryExecutor(newSingleThreadExecutor("数据库查询"))
                .build()
    }


}