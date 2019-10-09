package com.hezhihu89.fragment

import android.app.Application
import kotlin.properties.Delegates

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:13
 * 功能描述:
 */
class APP: Application() {

    companion object{
        var instances: APP by Delegates.notNull()

        fun instance() = instances
    }

    override fun onCreate() {
        super.onCreate()
        instances = this
    }
}