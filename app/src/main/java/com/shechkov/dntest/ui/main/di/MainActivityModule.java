package com.shechkov.dntest.ui.main.di;

import android.content.Context;

import com.shechkov.dntest.ui.base.di.ActivityModule;
import com.shechkov.dntest.ui.main.MainContract;
import com.shechkov.dntest.ui.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule implements ActivityModule {

    Context context;

    public MainActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @MainActivityScope
    MainContract.Presenter providePinCodePresenter(Context context) {
        return new MainPresenterImpl(context);
    }
}
