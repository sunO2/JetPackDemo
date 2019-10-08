package com.hezhihu89.jetpackdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hezhihu89.jetpackdemo.lifecycle.MyLifecycleObserver

class MainActivity : AppCompatActivity() {

    companion object {

        /**
         * 日志标记
         */
        val TAG:String = MainActivity::class.java.simpleName + ": HEZHIHU89"

    }

    private lateinit var myObserver: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObserver = MyLifecycleObserver()
        lifecycle.addObserver(myObserver)

    }
}