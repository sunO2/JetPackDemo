package com.hezhihu89.jetpackdemo.lifecycle

import android.util.Log
import androidx.lifecycle.*

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 08 日 14:37
 * 功能描述: 用于监听生命周期的类
 */
class MyLifecycleObserver: LifecycleEventObserver {
    /**
     * Called when a state transition event happens.
     *
     * @param source The source of the event
     * @param event The event
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }

    companion object {

        /**
         * 日志标记
         */
        private val TAG:String = MyLifecycleObserver::class.java.simpleName + ": HEZHIHU89"

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        Log.d(TAG,"MyLifecycleObserver onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        Log.d(TAG,"MyLifecycleObserver onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        Log.d(TAG,"MyLifecycleObserver onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        Log.d(TAG,"MyLifecycleObserver onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){
        Log.d(TAG,"MyLifecycleObserver onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        Log.d(TAG,"MyLifecycleObserver onDestroy")
    }
}