package com.chrisroid.ecommerce.data.remote

import retrofit2.HttpException
import java.io.IOException

class NetworkUtils {
    companion object {
        suspend fun <T> safeApiCall(
            apiCall: suspend () -> T
        ): Result<T> {
            return try {
                Result.Success(apiCall.invoke())
            } catch (e: IOException) {
                Result.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                Result.Error("HTTP error: ${e.message}")
            } catch (e: Exception) {
                Result.Error("Unknown error: ${e.message}")
            }
        }
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}