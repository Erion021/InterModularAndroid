package zubkov.vadim.pruebasandroiddiseo.dependecyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zubkov.vadim.pruebasandroiddiseo.screens.login.data.network.UserClient
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.data.network.MapClient
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.client.CommentClient
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.client.MenuClient
import zubkov.vadim.pruebasandroiddiseo.screens.register.data.network.RegisterClient
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.PersonClient
import javax.inject.Singleton

private val IP_NOTE = "http://192.168.153.122:8080/"
private val IP_LOCALHOST = "http://10.0.2.2:8080/"
private val IP_COMP = "http://192.168.1.142:8080/"
var Token : String = ""

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            request.addHeader("Accept", "application/json")
            request.addHeader("Authorization","Bearer $Token")
            val actualRequest = request.build()
            it.proceed(actualRequest)
        }
    }

    @Singleton
    @Provides
    fun getHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(IP_LOCALHOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build()
    }

    @Singleton
    @Provides
    fun provideUserClient(retrofit: Retrofit) : UserClient {
        return retrofit.create(UserClient::class.java)
    }

    @Singleton
    @Provides
    fun provideMapClient(retrofit: Retrofit) : MapClient {
        return retrofit.create(MapClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterClient(retrofit: Retrofit) : RegisterClient {
        return retrofit.create(RegisterClient::class.java)
    }

    @Singleton
    @Provides
    fun provideMenuClient(retrofit: Retrofit) : MenuClient {
        return retrofit.create(MenuClient::class.java)
    }

    @Singleton
    @Provides
    fun providePersonClient(retrofit: Retrofit) : PersonClient {
        return retrofit.create(PersonClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentClient(retrofit: Retrofit) : CommentClient {
        return retrofit.create(CommentClient::class.java)
    }
}