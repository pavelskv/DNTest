package com.shechkov.dntest.ui.main;

import android.content.Context;

import com.shechkov.dntest.BaseApp;
import com.shechkov.dntest.data.api.ApiService;
import com.shechkov.dntest.model.News;
import com.shechkov.dntest.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    ApiService apiService;

    public MainPresenterImpl(Context context) {
        ((BaseApp) context).getComponentManager()
                .getAppComponent()
                .inject(this);
    }

    @Override
    public void loadData() {
        getView().clear();
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
}
