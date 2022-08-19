package com.techyourchance.dagger2course.screens.common.dialogs

import androidx.fragment.app.FragmentManager

class DialogManager(
    private val supportFragmentManager: FragmentManager
) {

    fun showServerErrorDialog() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}
