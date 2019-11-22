package com.shechkov.dntest.ui.base.di;

public interface ActivityComponent<A> {
    void inject(A activity);
}