package com.hezhihu89.kt

import android.view.View
import android.widget.TextView

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 09 日 17:29
 * 功能描述:
 */
fun TextView.value(): String{
    return text.toString()
}

fun View.click(click: (View)-> Unit){
    setOnClickListener{
        click(it)
    }
}