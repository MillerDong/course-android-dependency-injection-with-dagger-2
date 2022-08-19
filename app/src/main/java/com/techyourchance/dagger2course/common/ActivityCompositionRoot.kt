package com.techyourchance.dagger2course.common

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.screens.common.dialogs.DialogManager
import com.techyourchance.dagger2course.screens.common.navigation.NavigationManager
import com.techyourchance.dagger2course.screens.common.networking.FetchQuestionDetailUseCase
import com.techyourchance.dagger2course.screens.common.networking.FetchQuestionsUseCase

class ActivityCompositionRoot (
    private val activity: Activity,
    private val fragmentManager: FragmentManager
) {

    private val appCompositionRoot by lazy {
        (activity.application as MyApplication).appCompositionRoot
    }

    private val stackoverflowApi by lazy {
        appCompositionRoot.stackoverflowApi
    }

    val fetchQuestionsUseCase get() =  FetchQuestionsUseCase(stackoverflowApi)
    val fetchQuestionDetailUseCase get() = FetchQuestionDetailUseCase(stackoverflowApi)
    val dialogManager by lazy {
        DialogManager(fragmentManager)
    }
    val navigationManager by lazy {
        NavigationManager(activity)
    }
}
