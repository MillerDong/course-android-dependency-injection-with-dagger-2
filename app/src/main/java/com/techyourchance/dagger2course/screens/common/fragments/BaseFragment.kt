package com.techyourchance.dagger2course.screens.common.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.techyourchance.dagger2course.common.ActivityCompositionRoot
import com.techyourchance.dagger2course.screens.common.activities.BaseActivity

open class BaseFragment: Fragment() {

    lateinit var compositionRoot : ActivityCompositionRoot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositionRoot = (requireActivity() as BaseActivity).compositionRoot
    }
}