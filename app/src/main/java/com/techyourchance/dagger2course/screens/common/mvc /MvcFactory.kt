package com.techyourchance.dagger2course.screens.common.mvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsMvc
import com.techyourchance.dagger2course.screens.questionslist.QuestionListMvc

class MvcFactory(private val layoutInflater: LayoutInflater) {

    fun newQuestionDetailsMvcFactory(parent: ViewGroup?) : QuestionDetailsMvc {
        return QuestionDetailsMvc(layoutInflater, parent)
    }

    fun newQuestionListMvc(parent: ViewGroup?) : QuestionListMvc {
        return QuestionListMvc(layoutInflater, parent)
    }
}
