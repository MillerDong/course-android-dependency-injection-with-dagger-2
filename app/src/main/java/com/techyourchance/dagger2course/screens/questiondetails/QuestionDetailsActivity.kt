package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.screens.common.dialogs.ServerErrorDialogFragment
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionDetailsActivity : AppCompatActivity(), QuestionDetailsMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionDetailsMvc: QuestionDetailsMvc
    private lateinit var stackoverflowApi: StackoverflowApi

    private lateinit var questionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionDetailsMvc = QuestionDetailsMvc(LayoutInflater.from(this), null)
        setContentView(questionDetailsMvc.rootView)

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

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
                val response = stackoverflowApi.questionDetails(questionId)
                if (response.isSuccessful && response.body() != null) {
                    val questionBody = response.body()!!.question.body
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        questionDetailsMvc.setQuestionBody(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY))

                    } else {
                        @Suppress("DEPRECATION")
                        questionDetailsMvc.setQuestionBody(Html.fromHtml(questionBody))
                    }
                } else {
                    onFetchFailed(response.errorBody().toString())
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed(t.message)
                }
            } finally {
                questionDetailsMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed(error: String?) {
        Log.i("MILLER", "error = $error")
        supportFragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
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