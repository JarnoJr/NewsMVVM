package com.example.newsmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.newsmvvm.framework.datasource.cache.database.AppDatabase
import com.example.newsmvvm.framework.datasource.cache.database.dao.NewsDao
import com.example.newsmvvm.framework.datasource.network.RetroAPI
import com.example.newsmvvm.util.Consts.Companion.BASE_URL
import com.example.newsmvvm.util.Consts.Companion.CONNECTION_TIMEOUT
import com.example.newsmvvm.util.Consts.Companion.READ_TIMEOUT
import com.example.newsmvvm.util.Consts.Companion.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetroInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetroApi(retrofit: Retrofit): RetroAPI {
        return retrofit.create(RetroAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "NewsDb").fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }
}