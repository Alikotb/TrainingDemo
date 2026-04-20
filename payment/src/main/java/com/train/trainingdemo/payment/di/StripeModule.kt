package com.train.trainingdemo.payment.di

import android.content.Context
import com.stripe.android.Stripe
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StripeModule {

    @Provides
    @Singleton
    fun provideStripe(@ApplicationContext context: Context): Stripe {
        // Use a test publishable key
        return Stripe(context, "pk_test_51BTj7S2eX4qn7p00nU8N7a7p")
    }
}
