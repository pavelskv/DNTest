package com.shechkov.dntest.di.component;

import android.app.Application;

import com.shechkov.dntest.BaseApp;
import com.shechkov.dntest.di.module.AppModule;
import com.shechkov.dntest.di.module.NetworkModule;
import com.shechkov.dntest.ui.main.MainPresenterImpl;

import dagger.Component;

@Component(modules = {AppModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(BaseApp mApplication);
    void inject(MainPresenterImpl mainPresenter);

}
