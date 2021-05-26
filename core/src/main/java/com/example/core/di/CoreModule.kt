package com.example.core.di

import androidx.room.Room
import com.example.core.data.DataMTRepository
import com.example.core.data.source.local.LocalDataSource
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.example.core.domain.repository.IDataMTRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.Network.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.example.core.data.source.local.room.DataMTDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiService
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<DataMTDatabase>().dataMTDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("example".toCharArray())
        val fac = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            DataMTDatabase::class.java, "datamt.db"
        ).fallbackToDestructiveMigration()
                .openHelperFactory(fac)
                .build()
    }
}

val networkModule = module {
    single {
        val host = "api.themoviedb.org"
        val certificatePin = CertificatePinner.Builder()
                .add(host, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
                .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .certificatePinner(certificatePin)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IDataMTRepository> {
        DataMTRepository(
            get(),
            get(),
            get()
        )
    }
}