package com.shechkov.dntest.ui.base;

public interface IBasePresenter<V extends IBaseView> {

    void attachView(V mvpView);

    void detachView();

    void destroy();
}
