package com.techyourchance.dagger2course.screens.common.mvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes


open class BaseMvc<LISTENER>(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    @LayoutRes layout: Int
) {

    protected val listeners = mutableSetOf<LISTENER>()

    val rootView: View = inflater.inflate(layout, parent, false)
    val context: Context = rootView.context

    fun registerListener(listener: LISTENER) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER) {
        listeners.remove(listener)
    }

    protected fun<T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }
}