package com.shechkov.dntest.di.component;

import android.app.Application;

import com.shechkov.dntest.BaseApp;
import com.shechkov.dntest.di.module.AppModule;
import com.shechkov.dntest.di.module.NetworkModule;

import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(BaseApp mApplication);

}
