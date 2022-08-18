package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.screens.common.dialogs.DialogManager
import com.techyourchance.dagger2course.screens.common.navigation.NavigationManager
import com.techyourchance.dagger2course.screens.common.networking.FetchQuestionDetailUseCase
import kotlinx.coroutines.*

class QuestionDetailsActivity : AppCompatActivity(), QuestionDetailsMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val fetchQuestionDetailUseCase = FetchQuestionDetailUseCase()
    private lateinit var questionDetailsMvc: QuestionDetailsMvc
    private lateinit var questionId: String
    private lateinit var dialogManager: DialogManager
    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionDetailsMvc = QuestionDetailsMvc(LayoutInflater.from(this), null)
        dialogManager = DialogManager(supportFragmentManager)
        navigationManager = NavigationManager(this)
        setContentView(questionDetailsMvc.rootView)

        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
    }

    override fun onStart() {
        super.onStart()
        questionDetailsMvc.registerListener(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        questionDetailsMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            questionDetailsMvc.showProgressIndication()
            try {
                when (val result = fetchQuestionDetailUseCase.fetchQuestionDetail(questionId)) {
                    is FetchQuestionDetailUseCase.Result.Success -> {
                        questionDetailsMvc.bindQuestionBody(result.questionDetail)
                    }
                    is FetchQuestionDetailUseCase.Result.Failure -> {
                        onFetchFailed(result.message)
                    }
                }
            } finally {
                questionDetailsMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed(error: String?) {
        Log.i("MILLER", "error = $error")
        dialogManager.showServerErrorDialog()
    }

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

    override fun onNavigationUpClicked() {
        onBackPressed()
    }
}