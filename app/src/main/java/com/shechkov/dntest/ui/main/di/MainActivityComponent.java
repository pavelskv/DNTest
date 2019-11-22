package com.shechkov.dntest.ui.main.di;

import com.shechkov.dntest.ui.base.di.ActivityComponent;
import com.shechkov.dntest.ui.base.di.ActivityComponentBuilder;
import com.shechkov.dntest.ui.main.MainActivity;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent  extends ActivityComponent<MainActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<MainActivityComponent, MainActivityModule> {

    }
}
