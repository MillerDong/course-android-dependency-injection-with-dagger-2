package com.techyourchance.dagger2course.common

import android.app.Activity
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.techyourchance.dagger2course.screens.common.navigation.NavigationManager

class ActivityCompositionRoot (
    private val appCompositionRoot: AppCompositionRoot,
    private val activity: Activity,
    val fragmentManager: FragmentManager
) {

    val stackoverflowApi by lazy {
        appCompositionRoot.stackoverflowApi
    }

    val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    val navigationManager by lazy {
        NavigationManager(activity)
    }
}
