package com.alexcarbonell.data.remote

import com.alexcarbonell.domain.exception.EndOfPaginationException
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

object ApiExceptionFactory {

    fun create(exception: HttpException): Exception {
        val type = object : TypeToken<HttpError>() {}.type
        val errorResponse: HttpError? =
            Gson().fromJson(exception.response()?.errorBody()?.charStream(), type)

        return errorResponse?.errorMessage?.let { errorMessage ->
            when (errorMessage) {
                "There is nothing here" -> EndOfPaginationException
                else -> Exception()
            }
        } ?: Exception()
    }

    data class HttpError(
        @SerializedName("error")
        val errorMessage: String?
    )
}
