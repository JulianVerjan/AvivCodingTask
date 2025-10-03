package com.test.network.model.mapper

import com.test.domain.networkresult.NetworkResult
import com.test.network.NetworkResponse

fun <T : Any, U : Any> NetworkResponse<T, Any>.toRepositoryResult(mapper: (from: T) -> U): NetworkResult<U> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResult.Success(
            if (this.body != null)
                mapper(this.body)
            else null
        )

        is NetworkResponse.ApiError -> NetworkResult.Fail(this)
        is NetworkResponse.NetworkError -> NetworkResult.Exception(this.error)
        is NetworkResponse.UnknownError -> NetworkResult.Exception(this.error)
    }
}
