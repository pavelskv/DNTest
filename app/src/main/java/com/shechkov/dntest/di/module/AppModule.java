package com.shechkov.dntest.di.module;

import android.content.Context;

import com.shechkov.dntest.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    Context provideContext(){
        return context;
    }

}
