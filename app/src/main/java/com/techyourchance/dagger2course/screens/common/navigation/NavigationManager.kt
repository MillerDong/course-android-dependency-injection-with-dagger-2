package com.techyourchance.dagger2course.screens.common.navigation

import android.app.Activity
import android.content.Context
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsActivity

class NavigationManager(
    private val activity: Activity
) {

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun navigateToQuestionDetailActivity(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }
}
