package edu.ucne.james_urena_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.james_urena_ap2_p2.data.remote.GastoApi
import edu.ucne.james_urena_ap2_p2.data.remote.remotedatasource.GastoRemoteDataSource
import edu.ucne.james_urena_ap2_p2.data.repository.GastoRepositoryImpl
import edu.ucne.james_urena_ap2_p2.domain.repository.GastoRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): GastoApi {
        return Retrofit.Builder()
            .baseUrl("https://api-2026-h7eddqgydxc0fmau.eastus2-01.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GastoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGastoRemoteDataSource(api: GastoApi): GastoRemoteDataSource {
        return GastoRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideGastoRepository(remoteDataSource: GastoRemoteDataSource): GastoRepository {
        return GastoRepositoryImpl(remoteDataSource)

    }
}