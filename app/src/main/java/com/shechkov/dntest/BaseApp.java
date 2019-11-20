package com.shechkov.dntest;

import android.app.Activity;
import android.app.Application;

import com.shechkov.dntest.di.component.ApplicationComponent;
import com.shechkov.dntest.di.component.DaggerApplicationComponent;
import com.shechkov.dntest.di.module.NetworkModule;

public class BaseApp extends Application {

   public ApplicationComponent applicationComponent;

   @Override
   public void onCreate() {
      super.onCreate();

     applicationComponent = DaggerApplicationComponent.builder()
             .networkModule(new NetworkModule())
             .build();

     applicationComponent.inject(this);
   }

    public static BaseApp get(Activity activity){
        return (BaseApp) activity.getApplication();
    }

   public ApplicationComponent getApplicationComponent(){
       return applicationComponent;
   }
}
