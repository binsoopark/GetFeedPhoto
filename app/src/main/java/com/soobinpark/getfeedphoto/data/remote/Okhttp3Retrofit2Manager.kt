package com.soobinpark.getfeedphoto.data.remote

import android.util.Log
import com.google.api.client.auth.oauth.OAuthHmacSigner
import com.google.api.client.auth.oauth.OAuthParameters
import com.google.api.client.http.GenericUrl
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object Okhttp3Retrofit2Manager {
    const val TAG = "Okhttp3Retrofit2Manager"
    private val ALL_TIMEOUT = 10L
    private val TWITTER_API_HOST = "https://api.twitter.com/"
    private val TWITTER_CONSUMER_KEY = "j5YhaJZRS57fbWUHwjokhtm6b"
    private val TWITTER_CONSUMER_SECRET = "iiH8F2WWw1xPqkpyeA6gTAKGwDowITyr3RhcHiz7EQmDGFW7VE"
    private val TWITTER_ACCESS_TOKEN = "1159133442891587584-Nuue2z3B2GKyVA3k75Meo5Z7vC1v1T"
    private val TWITTER_TOKEN_SECRET = "GNYa04Vvam5A2piV78GFoQPPhP8xJEd6kf9QWkNUmGucR"

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    init{
        /*
         * 로깅 인터셉터 연결
         */
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BASIC

        val oAuthParams = OAuthParameters()
        oAuthParams.consumerKey = TWITTER_CONSUMER_KEY
        oAuthParams.token = TWITTER_ACCESS_TOKEN

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
            addInterceptor(AuthorizationInterceptor(oAuthParams))
            connectTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)

        }.build()
        /*
         * Retrofit2 + OKHttp3를 연결한다
         */
        retrofit = Retrofit.Builder().apply{
            baseUrl(TWITTER_API_HOST)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())//gson을 이용해 json파싱
        }.build()
    }

    private class AuthorizationInterceptor(val oAuthParams: OAuthParameters) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            oAuthParams.computeNonce()
            oAuthParams.computeTimestamp()
            oAuthParams.version = "1.0"
            val genericUrl = GenericUrl(originalRequest.url().url())
            val oauthHmacSigner = OAuthHmacSigner()
            oauthHmacSigner.clientSharedSecret = TWITTER_CONSUMER_SECRET
            oauthHmacSigner.tokenSharedSecret = TWITTER_TOKEN_SECRET
            oAuthParams.signer = oauthHmacSigner
            oAuthParams.computeSignature("GET", genericUrl)

            Log.d(TAG, "getAuthorizationHeader: ${oAuthParams.authorizationHeader}")

            val builder = originalRequest.newBuilder().apply {
                header("Authorization", oAuthParams.authorizationHeader)
                method(originalRequest.method(), originalRequest.body())
            }

            val request = builder.build()
            return chain.proceed(request)
        }
    }

    /*
     * 현재 선언된 요청 인터페이스 객체(RetrofitInterface)를 리턴한다
     */
    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }
}