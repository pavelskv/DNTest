package com.shechkov.dntest.di.module;

import com.shechkov.dntest.api.ApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/everything/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

}
