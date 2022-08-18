package com.techyourchance.dagger2course.screens.common.networking

import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.Question
import kotlinx.coroutines.CancellationException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchQuestionsUseCase {

    sealed class Result {
        data class Success(val questions: List<Question>): Result()
        object Failure : Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    suspend fun fetchQuestions(): Result {
//        withContext(Dispatchers.IO)
        return try {
            val response = stackoverflowApi.lastActiveQuestions(20)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.questions)
            } else {
                Result.Failure
            }
        } catch (t: Throwable) {
            if (t !is CancellationException) {
                Result.Failure
            } else {
                throw t
            }
        }
    }
}