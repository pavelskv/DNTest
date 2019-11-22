package com.shechkov.dntest.di.component;

import android.content.Context;

import com.shechkov.dntest.di.module.AppModule;
import com.shechkov.dntest.di.module.NetworkModule;
import com.shechkov.dntest.di.module.NewsDbModule;
import com.shechkov.dntest.ui.base.di.ActivityComponent;
import com.shechkov.dntest.ui.base.di.ActivityComponentBuilder;
import com.shechkov.dntest.ui.base.di.ActivityModule;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ComponentManager {

    private final Context context;

    @Inject
    Map<Class<?>, Provider<ActivityComponentBuilder>> builders;

    private Map<Class<?>, ActivityComponent> components;
    private ApplicationComponent appComponent;

    public ComponentManager(Context context) {
        this.context = context;
    }

    public void init() {
        appComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(context))
                .networkModule(new NetworkModule())
                .newsDbModule(new NewsDbModule())
                .build();
        appComponent.injectComponentManager(this);
        components = new HashMap<>();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }


    public ActivityComponent getActivityComponent(Class<?> cls) {
        return getActivityComponent(cls, null);
    }

    public ActivityComponent getActivityComponent(Class<?> cls, ActivityModule module) {
        ActivityComponent component = components.get(cls);
        if (component == null) {
            ActivityComponentBuilder builder = builders.get(cls).get();
            if (module != null) {
                builder.module(module);
            }
            component = builder.build();
            components.put(cls, component);
        }
        return component;
    }

    public void releaseActivityComponent(Class<?> cls) {
        components.put(cls, null);

    }

}