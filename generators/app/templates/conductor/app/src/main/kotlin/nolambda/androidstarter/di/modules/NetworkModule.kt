package <%= appPackage %>.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import <%= appPackage %>.BuildConfig
import <%= appPackage %>.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val DEFAULT_TIMEOUT = 30L
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideBaseUrl(): String = "https://httpbin.org/"

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, client: OkHttpClient, gson: Gson): Retrofit {

        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor({ message -> Timber.d(message) }).apply {
                level = when {
                    BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.NONE
                    else -> HttpLoggingInterceptor.Level.BODY
                }
            }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
