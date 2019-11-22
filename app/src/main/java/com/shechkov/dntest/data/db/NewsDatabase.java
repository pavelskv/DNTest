package com.shechkov.dntest.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shechkov.dntest.model.Article;

@Database(entities = Article.class, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

}
