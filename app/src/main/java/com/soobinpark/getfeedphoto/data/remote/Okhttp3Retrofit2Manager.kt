package com.soobinpark.getfeedphoto.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object Okhttp3Retrofit2Manager {
    private val ALL_TIMEOUT = 10L
    private val TWITTER_API_HOST = "https://api.twitter.com/1.1/"
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


        okHttpClient = OkHttpClient().newBuilder().apply {

            addInterceptor(httpLogging)
            addInterceptor(HeaderSettingInterceptor())
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

    /*
 *  Request Header를 세팅하는 Interceptor
 */
    private  class HeaderSettingInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val chainRequest = chain.request()
            /**
             * GET /1.1/statuses/home_timeline.json HTTP/1.1
            Host: api.twitter.com
            Authorization: OAuth oauth_consumer_key="j5YhaJZRS57fbWUHwjokhtm6b",oauth_token="1159133442891587584-Nuue2z3B2GKyVA3k75Meo5Z7vC1v1T",oauth_signature_method="HMAC-SHA1",oauth_timestamp="1565411058",oauth_nonce="SDPlk3WzR2p",oauth_version="1.0",oauth_signature="g651b717Nv5hDG1N9P8sbEvNGbk%3D"
            User-Agent: PostmanRuntime/7.15.2
            Accept:
            Cache-Control: no-cache
            Postman-Token: 0c7f3e27-12a1-4edc-98e9-0da211a90c6d,9a6ac7f2-9843-4c05-b590-a1c2ee2d8466
            Host: api.twitter.com
            Cookie: personalization_id="v1_CCbERvXS6YFEgKmbso3ZbA=="; guest_id=v1%3A156527505685749018; lang=ko
            Accept-Encoding: gzip, deflate
            Connection: keep-alive
            cache-control: no-cache


            */
            // Accept */*
            val request = chainRequest.newBuilder().apply{
                addHeader("Accept", "application/json")
                addHeader("oauth_token", TWITTER_CONSUMER_KEY)
                addHeader("oauth_consumer_key", TWITTER_CONSUMER_KEY)
                addHeader("oauth_consumer_key", TWITTER_CONSUMER_KEY)
                addHeader("oauth_consumer_key", TWITTER_CONSUMER_KEY)
            }.build()

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