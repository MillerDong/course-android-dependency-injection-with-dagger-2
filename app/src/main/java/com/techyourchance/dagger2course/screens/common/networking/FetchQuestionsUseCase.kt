package com.techyourchance.dagger2course.screens.common.networking

import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.Question
import kotlinx.coroutines.CancellationException

class FetchQuestionsUseCase(private val stackoverflowApi: StackoverflowApi) {

    sealed class Result {
        data class Success(val questions: List<Question>): Result()
        object Failure : Result()
    }

    suspend fun fetchQuestions(): Result {
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