package com.shechkov.dntest.ui.base;

public abstract class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() {

    }

    public T getView(){
        return view;
    }

    protected boolean isViewAttached(){
        return view != null;
    }
}
