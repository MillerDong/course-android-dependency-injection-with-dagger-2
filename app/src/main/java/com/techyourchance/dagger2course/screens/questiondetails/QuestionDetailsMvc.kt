package com.techyourchance.dagger2course.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.screens.common.mvc.BaseMvc
import com.techyourchance.dagger2course.screens.common.toolbar.MyToolbar

class QuestionDetailsMvc(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseMvc<QuestionDetailsMvc.Listener>(inflater, parent, R.layout.layout_question_details){

    interface Listener {
        fun onNavigationUpClicked()
    }

    private val toolbar: MyToolbar = findViewById(R.id.toolbar)
    private val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
    private val txtQuestionBody: TextView = findViewById(R.id.txt_question_body)

    init {
        // init toolbar
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onNavigationUpClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh.isEnabled = false
    }

    fun bindQuestionBody(questionBody: String) {
        txtQuestionBody.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(questionBody)
        }
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }
}