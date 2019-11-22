package com.shechkov.dntest.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.shechkov.dntest.model.Article;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<Article> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article favoriteShow);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Article> items);

    @Delete
    void remove(Article favoriteShow);
}
