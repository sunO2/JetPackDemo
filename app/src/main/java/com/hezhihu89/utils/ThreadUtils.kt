package com.hezhihu89.utils

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 14 日 18:49
 * 功能描述:
 */
/**
 * The default thread factory.
 */

fun newSingleThreadExecutor(groupName: String): ExecutorService {
    return ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            LinkedBlockingQueue(),
            DefaultThreadFactory(groupName))
}

class DefaultThreadFactory internal constructor(groupName: String) : ThreadFactory {
    private val group: ThreadGroup
    private val threadNumber = AtomicInteger(1)
    private val namePrefix: String

    init {
        val s = System.getSecurityManager()
        group = if (s != null)
            s.threadGroup
        else
            Thread.currentThread().threadGroup
        namePrefix = "Thread-pool-$groupName" +
                poolNumber.getAndIncrement() +
                "-thread-"
    }

    override fun newThread(r: Runnable): Thread {
        val t = Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0)
        if (t.isDaemon)
            t.isDaemon = false
        if (t.priority != Thread.NORM_PRIORITY)
            t.priority = Thread.NORM_PRIORITY
        return t
    }

    companion object {
        private val poolNumber = AtomicInteger(1)
    }
}