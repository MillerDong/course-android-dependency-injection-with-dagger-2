package com.techyourchance.dagger2course.screens.common.networking

import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchQuestionDetailUseCase {

    sealed class Result {
        data class Success(val questionDetail: String): Result()
        data class Failure(val message: String): Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

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