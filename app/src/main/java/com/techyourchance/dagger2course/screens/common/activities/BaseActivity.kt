package com.techyourchance.dagger2course.screens.common.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.common.ActivityCompositionRoot

open class BaseActivity : AppCompatActivity() {

    lateinit var compositionRoot : ActivityCompositionRoot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositionRoot = ActivityCompositionRoot((application as MyApplication).appCompositionRoot, this, supportFragmentManager)
    }
}
