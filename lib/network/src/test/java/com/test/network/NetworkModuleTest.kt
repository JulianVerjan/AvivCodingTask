package com.test.network

import com.test.network.interceptor.RequestInterceptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class NetworkModuleTest {

    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule
    }

    @Test
    fun verifyProvidedHttpClient() {
        val httpClient = networkModule.provideHttpClient(RequestInterceptor())
        assertEquals(20000, httpClient.connectTimeoutMillis)
    }

    @Test
    fun verifyProvidedRetrofitBuilder() {
        val retrofit = networkModule.provideRetrofitBuilder(mock(), mock())
        assertEquals(
            BuildConfig.API_BASE_URL,
            retrofit.baseUrl().toString()
        )
    }
}
