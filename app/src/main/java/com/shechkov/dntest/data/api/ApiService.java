package com.shechkov.dntest.data.api;

import com.shechkov.dntest.model.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("everything")
    Observable<News> getNews(@Query("q") String query, @Query("from") String date, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey, @Query("page") int page);

}
