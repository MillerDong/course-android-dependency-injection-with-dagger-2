package com.techyourchance.dagger2course.screens.questiondetails

import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.screens.common.toolbar.MyToolbar

class QuestionDetailsMvc(
    inflater: LayoutInflater,
    parent: ViewGroup?
) {

    interface Listener {
        fun onNavigationUpClicked()
    }

    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val txtQuestionBody: TextView

    private val listeners = mutableSetOf<Listener>()

    val rootView: View = inflater.inflate(R.layout.layout_question_details, parent, false)

    init {
        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onNavigationUpClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun setQuestionBody(text: Spanned) {
        txtQuestionBody.text = text
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    private fun<T: View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }
}