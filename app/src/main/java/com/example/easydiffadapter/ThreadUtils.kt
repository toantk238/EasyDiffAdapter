package com.example.easydiffadapter

import android.os.Handler
import android.os.Looper
import android.os.Process
import com.facebook.imagepipeline.core.PriorityThreadFactory

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by freesky1102 on 2/6/18.
 */

object ThreadUtils {

    private val sWorkerExecutor: MyWorkerExecutor by lazy { MyWorkerExecutor() }

    private val executor: MyWorkerExecutor
        @Synchronized get() {
            return sWorkerExecutor
        }

    private class MyWorkerExecutor constructor() {

        private val forBackgroundTasks: ThreadPoolExecutor

        private val mainHandler: Handler

        init {

            val backgroundPriorityThreadFactory =
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND)

            forBackgroundTasks = ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2, 60L,
                TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(), backgroundPriorityThreadFactory
            )

            mainHandler = Handler(Looper.getMainLooper())
        }

        internal fun forBackgroundTasks(): ThreadPoolExecutor {
            return forBackgroundTasks
        }

        internal fun mainHandler(): Handler {
            return mainHandler
        }

        companion object {

            internal val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
        }
    }

    @JvmStatic
    fun onWorker(runnable: Runnable) {
        executor.forBackgroundTasks().submit(runnable)
    }

    @JvmStatic
    fun onWorker(runnable: () -> Unit) {
        executor.forBackgroundTasks().submit { runnable.invoke() }
    }

    @JvmStatic
    fun onMain(runnable: Runnable) {
        executor.mainHandler().post(runnable)
    }

    @JvmStatic
    fun onMain(runnable: () -> Unit) {
        executor.mainHandler().post { runnable.invoke() }
    }

    @JvmStatic
    fun delayOnMain(runnable: Runnable, delayMillis: Long) {
        executor.mainHandler().postDelayed(runnable, delayMillis)
    }

    @JvmStatic
    fun delayOnMain(runnable: () -> Unit, delayMillis: Long) {
        executor.mainHandler().postDelayed({ runnable.invoke() }, delayMillis)
    }
}
