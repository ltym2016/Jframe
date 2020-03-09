package com.luys.library.base

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author luys
 * @describe
 * @date 2020/3/6
 * @email samluys@foxmail.com
 */
open class SingleLiveEvent<T>:MutableLiveData<T>() {

    private val TAG = "SingleLiveEvent"
    private val mPending = AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG,
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: T?) {
        super.setValue(value)
    }

    fun call() {
        setValue(null)
    }

}