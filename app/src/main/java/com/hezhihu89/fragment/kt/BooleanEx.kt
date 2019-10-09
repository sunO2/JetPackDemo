package com.hezhihu89.fragment.kt

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:43
 * 功能描述:
 */
fun <T> Boolean.isTrue(check: ()-> T): Boolean{
    if(this)check()
    return this
}

fun <T> Boolean.isFalse(check: ()-> T): Boolean{
    if(!this)check()
    return this
}
