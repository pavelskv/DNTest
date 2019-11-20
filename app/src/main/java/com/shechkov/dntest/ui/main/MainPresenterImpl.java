package com.shechkov.dntest.ui.main;

import android.app.Application;

import com.shechkov.dntest.BaseApp;
import com.shechkov.dntest.api.ApiService;
import com.shechkov.dntest.di.component.ApplicationComponent;
import com.shechkov.dntest.models.News;
import com.shechkov.dntest.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public ApiService apiService;

    public MainPresenterImpl(Application applicationComponent) {
        ((BaseApp) applicationComponent).getApplicationComponent().inject(this);
    }

    @Override
    public void loadData() {
        getView().showLoading();

        apiService.getNews("android", "2019-04-00", "publishedAt", "26eddb253e7840f988aec61f2ece2907", 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(News news) {
                        getView().clear();
                        getView().setData(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    @Override
    public void loadMore(int page) {
        apiService.getNews("android", "2019-04-00", "publishedAt", "26eddb253e7840f988aec61f2ece2907", page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(News news) {
                        getView().setData(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showFooterError(true, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    @Override
    public void viewIsReady() {

    }
}
