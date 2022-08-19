package com.techyourchance.dagger2course.screens.common.networking

import com.techyourchance.dagger2course.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException

class FetchQuestionDetailUseCase(private val stackoverflowApi: StackoverflowApi) {

    sealed class Result {
        data class Success(val questionDetail: String): Result()
        data class Failure(val message: String): Result()
    }

    suspend fun fetchQuestionDetail(questionId: String): Result {
        return try {
            val response = stackoverflowApi.questionDetails(questionId)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.question.body)
            } else {
                Result.Failure(response.errorBody().toString())
            }
        } catch (t: Throwable) {
            if (t !is CancellationException) {
                Result.Failure(t.message ?: "unknown error")
            } else {
                throw t
            }
        }
    }
}