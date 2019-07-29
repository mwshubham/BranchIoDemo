package com.example.branchiodemo

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * This Class is just to display log messages
 *
 * --to see the log under this particular Tag in Info
 * --Message you want to print.
 *
 * -- Adding support to null as all the this class is also used in the ${DeBug} class
 * which is not null safe compatible...
 *
 */
object GlobalLoggerUtils {

    private val toShowLog = BuildConfig.DEBUG

    @JvmStatic
    fun showLog(TAG: String?, message: String?) {
        try {
            if (toShowLog && TAG != null && message != null) {
                Log.i(TAG, message + " " + "[Thread: " + Thread.currentThread().name + "]")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun logError(TAG: String?, message: String?) {
        if (toShowLog && TAG != null && message != null) {
            Log.e(TAG, message + " " + "[Thread: " + Thread.currentThread().name + "]")
        }
    }

    @JvmStatic
    fun largeLog(tag: String, message: String?) {
        if (toShowLog && message != null) {
            try {
                if (message.length > 4000) {
                    showLog(tag, message.subSequence(0, 4000).toString())
                    largeLog(tag, message.subSequence(4000, message.length - 1).toString())
                } else {
                    showLog(tag, message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun showToast(context: Context, MSG: String?) {
        if (toShowLog && MSG != null) {
            Toast.makeText(context, MSG, Toast.LENGTH_SHORT).show()
        }
    }

}