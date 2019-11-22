package com.shechkov.dntest.ui.main;

import com.shechkov.dntest.model.News;
import com.shechkov.dntest.ui.base.IBasePresenter;
import com.shechkov.dntest.ui.base.IBaseView;

public interface MainContract {

    interface View extends IBaseView{

        void setData(News news);

        void showLoading();

        void hideLoading();

        void showError(String message);

        void showFooterError(boolean show, String errorMsg);

        void clear();

    }

    interface Presenter extends IBasePresenter<View>{

        void loadData();

        void loadMore(int page);

    }

}
