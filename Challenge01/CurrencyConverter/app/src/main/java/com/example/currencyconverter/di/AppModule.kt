package com.example.currencyconverter.di

import com.example.currencyconverter.data.remote.ApiInterface
import com.example.currencyconverter.data.repositories.CurrencyConverterRepository
import com.example.currencyconverter.utilities.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }



    @Provides
    @Singleton
    fun provideCurrencyConverterRepository(api: ApiInterface): CurrencyConverterRepository {
        return CurrencyConverterRepository(api)
    }

}