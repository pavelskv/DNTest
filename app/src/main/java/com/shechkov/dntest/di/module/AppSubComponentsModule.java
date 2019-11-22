package com.shechkov.dntest.di.module;

import com.shechkov.dntest.ui.base.di.ActivityComponentBuilder;
import com.shechkov.dntest.ui.main.MainActivity;
import com.shechkov.dntest.ui.main.di.MainActivityComponent;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MainActivityComponent.class})
public class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(MainActivity.class)
    ActivityComponentBuilder provideSplashViewBuilder(MainActivityComponent.Builder builder) {
        return builder;
    }

}
