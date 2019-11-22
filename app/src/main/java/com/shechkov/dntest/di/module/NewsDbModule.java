package com.shechkov.dntest.di.module;

import android.app.Application;

import androidx.room.Room;

import com.shechkov.dntest.data.db.NewsDao;
import com.shechkov.dntest.data.db.NewsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDbModule {

    @Provides
    @Singleton
    NewsDatabase provideNewsDatabase(Application context){
        return Room.databaseBuilder(context,
                NewsDatabase.class, "news_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    NewsDao provideNewsDao(NewsDatabase newsDatabase){
        return newsDatabase.newsDao();
    }

}
