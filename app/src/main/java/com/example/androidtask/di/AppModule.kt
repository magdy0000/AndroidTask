package com.example.androidtask.di

import android.content.Context
import androidx.room.Room
import com.example.androidtask.data.local.MyDao
import com.example.androidtask.data.local.MyDataBase
import com.example.androidtask.data.network.ApiClient
import com.example.androidtask.data.network.ApiServices
import com.example.androidtask.utils.Const
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .callTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url

                    val url = originalUrl.newBuilder()
                        .addQueryParameter("api_key" , Const.API_KEY)
                        .build()

                    val requestBuilder = originalRequest.newBuilder().url(url)

                    val request = requestBuilder.build()
                    val response = chain.proceed(request)
                    response.code//status code
                    return response
                }
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext: Context): MyDataBase {
        return Room
            .databaseBuilder(appContext, MyDataBase::class.java, Const.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideDao (localDatabase: MyDataBase) : MyDao {
        return localDatabase.getMyDao()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
    @Provides
    @Singleton
    fun provideApiClient(apiService: ApiServices): ApiClient {
        return ApiClient(apiService)
    }

}