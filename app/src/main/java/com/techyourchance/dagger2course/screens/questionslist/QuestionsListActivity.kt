package com.techyourchance.dagger2course.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.screens.common.activities.BaseActivity
import com.techyourchance.dagger2course.screens.common.dialogs.DialogManager
import com.techyourchance.dagger2course.screens.common.navigation.NavigationManager
import com.techyourchance.dagger2course.screens.common.networking.FetchQuestionsUseCase
import kotlinx.coroutines.*

class QuestionsListActivity : BaseActivity(), QuestionListMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionListMvc: QuestionListMvc
    private lateinit var dialogManager: DialogManager
    private lateinit var navigationManager: NavigationManager
    private lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase
    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionListMvc = QuestionListMvc(LayoutInflater.from(this), null)
        fetchQuestionsUseCase = compositionRoot.fetchQuestionsUseCase
        dialogManager = compositionRoot.dialogManager
        navigationManager = compositionRoot.navigationManager
        setContentView(questionListMvc.rootView)
    }

    override fun onStart() {
        super.onStart()
        questionListMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        questionListMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            questionListMvc.showProgressIndication()
            try {
                when (val result = fetchQuestionsUseCase.fetchQuestions()) {
                    is FetchQuestionsUseCase.Result.Success -> {
                        questionListMvc.bindData(result.questions)
                        isDataLoaded = true
                    }
                    is FetchQuestionsUseCase.Result.Failure -> {
                        onFetchFailed()
                    }
                }
            } finally {
                questionListMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogManager.showServerErrorDialog()
    }


    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(questionId: String) {
        navigationManager.navigateToQuestionDetailActivity(questionId)
    }
}