package com.shechkov.dntest.ui.main;

import com.shechkov.dntest.models.News;
import com.shechkov.dntest.ui.base.IBasePresenter;
import com.shechkov.dntest.ui.base.IBaseView;

import java.util.List;

public interface MainContract {

    interface View extends IBaseView{

        void setData(News news);

        void showLoading();

        void hideLoading();

        void showError(String message);

        void showToast(String message);

        void showFooterError(boolean show, String errorMsg);

        void clear();

    }

    interface Presenter extends IBasePresenter<View>{

        void loadData();

        void loadMore(int page);

    }

}
