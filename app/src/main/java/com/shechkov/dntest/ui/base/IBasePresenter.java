package com.shechkov.dntest.ui.base;

public interface IBasePresenter<V extends IBaseView> {

    void attachView(V mvpView);

    void viewIsReady();

    void detachView();

    void destroy();
}
