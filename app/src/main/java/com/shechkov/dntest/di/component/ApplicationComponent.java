package com.shechkov.dntest.di.component;

import com.shechkov.dntest.di.module.AppSubComponentsModule;
import com.shechkov.dntest.di.module.NewsDbModule;
import com.shechkov.dntest.di.module.AppModule;
import com.shechkov.dntest.di.module.NetworkModule;
import com.shechkov.dntest.di.scope.AppScope;
import com.shechkov.dntest.ui.main.MainPresenterImpl;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, NetworkModule.class, NewsDbModule.class, AppSubComponentsModule.class})
public interface ApplicationComponent {

    void injectComponentManager(ComponentManager componentManager);

    void inject(MainPresenterImpl mainPresenter);

}
