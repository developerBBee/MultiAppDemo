package com.example.multiappdemo.di

import com.example.multiappdemo.ui.destinations.Destinations
import com.example.common.ui.destination.MainDestination
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DestinationModule {
    @Singleton
    @Provides
    fun provideMainDestination(): MainDestination {
        return Destinations
    }
}