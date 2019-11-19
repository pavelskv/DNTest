package com.shechkov.dntest;

import android.app.Application;

import com.shechkov.dntest.di.component.ApplicationComponent;
import com.shechkov.dntest.di.component.DaggerApplicationComponent;
import com.shechkov.dntest.di.module.NetworkModule;

public class BaseApp extends Application {

   ApplicationComponent applicationComponent;

   @Override
   public void onCreate() {
      super.onCreate();

     applicationComponent = DaggerApplicationComponent.builder()
             .networkModule(new NetworkModule())
             .build();

     applicationComponent.inject(this);
   }
}
