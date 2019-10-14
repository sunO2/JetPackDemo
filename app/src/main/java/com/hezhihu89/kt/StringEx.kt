package com.hezhihu89.kt

import android.text.TextUtils

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:35
 * 功能描述:
 */
fun checkEmpt(vararg text: String): Boolean{
    text.forEach {
        if(TextUtils.isEmpty(it)){
            return true
        }
    }
    return false
}