package com.test.domain.networkresult


sealed class NetworkResult<out T : Any> {
    /**
     * Success
     */
    data class Success<T : Any>(val result: T?) : NetworkResult<T>()

    /**
     * Failure
     */
    data class Fail<U : Any>(val errorResult: U) : NetworkResult<Nothing>()

    /**
     * Network error
     */
    data class Exception(val error: Throwable?) : NetworkResult<Nothing>()
}
