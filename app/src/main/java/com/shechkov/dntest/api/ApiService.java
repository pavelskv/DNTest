package com.shechkov.dntest.api;

import com.shechkov.dntest.models.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("everything")
    Observable<List<News>> getNews(@Query("q") String query, @Query("from") String date, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey, @Query("page") int page);

}
