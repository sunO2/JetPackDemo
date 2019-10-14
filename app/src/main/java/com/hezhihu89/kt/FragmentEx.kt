package com.hezhihu89.kt

import android.view.WindowManager
import androidx.fragment.app.Fragment

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 10 日 15:01
 * 功能描述:
 */
fun Fragment.fullScreen(){
    val lp = activity?.window?.attributes
    lp?.flags = lp?.flags?:0 or WindowManager.LayoutParams.FLAG_FULLSCREEN
    activity?.window?.attributes = lp
    activity?.window?.addFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

fun Fragment.noFullScreen(){
    val lp = activity?.window?.attributes
    lp?.flags = lp?.flags?:0 and WindowManager.LayoutParams.FLAG_FULLSCREEN
    activity?.window?.attributes = lp
    activity?.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}