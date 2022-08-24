package com.techyourchance.dagger2course.common

import com.techyourchance.dagger2course.screens.common.dialogs.DialogManager
import com.techyourchance.dagger2course.screens.common.mvc.MvcFactory
import com.techyourchance.dagger2course.screens.common.networking.FetchQuestionDetailUseCase
import com.techyourchance.dagger2course.screens.common.networking.FetchQuestionsUseCase

class PresentationCompositionRoot(activityCompositionRoot: ActivityCompositionRoot) {

    private val layoutInflater = activityCompositionRoot.layoutInflater
    private val stackoverflowApi = activityCompositionRoot.stackoverflowApi
    private val fragmentManager = activityCompositionRoot.fragmentManager
    val navigationManager = activityCompositionRoot.navigationManager

    val mvcFactory = MvcFactory(layoutInflater)

    val fetchQuestionsUseCase get() =  FetchQuestionsUseCase(stackoverflowApi)
    val fetchQuestionDetailUseCase get() = FetchQuestionDetailUseCase(stackoverflowApi)
    val dialogManager by lazy {
        DialogManager(fragmentManager)
    }

}