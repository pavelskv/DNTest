package com.shechkov.dntest;

import android.app.Application;
import android.content.Context;

import com.shechkov.dntest.di.component.ComponentManager;

public class BaseApp extends Application {

   private ComponentManager componentManager;

   @Override
   public void onCreate() {
      super.onCreate();

       componentManager = new ComponentManager(this);
       componentManager.init();
   }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public static BaseApp get(Context context){
        return (BaseApp) context.getApplicationContext();
    }
}
