package com.hezhihu89.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:00
 * 功能描述:
 */
interface BaseDao<T> {

    /**
     * 插入一条数据
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(element: T)

    /**
     * 插入多条数据
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: MutableList<T>)

    /**
     * 删除一条数据
     */
    @Delete
    fun delete(element: T)

    /**
     * 删除多条数据
     */
    @Delete
    fun deleteList(element: MutableList<T>)

    /**
     * 删除多条数据
     */
    @Delete
    fun deleteSome(vararg element: T)

    /**
     * 更新一条数据
     */
    @Update
    fun update(element: T)
}