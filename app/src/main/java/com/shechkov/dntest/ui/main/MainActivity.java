package com.shechkov.dntest.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.shechkov.dntest.BaseApp;
import com.shechkov.dntest.R;
import com.shechkov.dntest.interfaces.AdapterItemClick;
import com.shechkov.dntest.interfaces.PagintationAdapterCallback;
import com.shechkov.dntest.model.News;
import com.shechkov.dntest.ui.adapter.NewsAdapter;
import com.shechkov.dntest.ui.details.DetailsActivity;
import com.shechkov.dntest.ui.main.di.MainActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout errorLayout;
    private LinearLayoutManager linearLayoutManager;

    @Inject
    public MainContract.Presenter presenter;

    private int TOTAL_PAGES = 5;
    private boolean isLoading = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        BaseApp.get(this)
                .getComponentManager()
                .getActivityComponent(getClass(), new MainActivityModule(this))
                .inject(this);

        setTitle(getResources().getString(R.string.news_title));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setInitialPrefetchItemCount(5);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new NewsAdapter(Glide.with(this), new PagintationAdapterCallback() {
            @Override
            public void retryPageLoad() {
                presenter.loadMore(currentPage);
            }
        }, new AdapterItemClick() {
            @Override
            public void onItemClick(int position) {
                startActivity(DetailsActivity.newIntent(MainActivity.this, adapter.getItem(position)));
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(onScrollListener);

        presenter.attachView(this);
        presenter.loadData();
    }

    private void bindViews() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.rv_news);
        swipeRefreshLayout = findViewById(R.id.refresh_list);
        errorLayout = findViewById(R.id.error_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData();
            }
        });

        findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadData();
            }
        });
    }

    @Override
    public void setData(News news) {
        adapter.removeLoadingFooter();
        isLoading = false;
        adapter.addAll(news.getArticles());

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            BaseApp.get(this).getComponentManager().releaseActivityComponent(getClass());
        }
    }

    @Override
    public void showLoading() {
        errorLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFooterError(boolean show, String errorMsg) {
        adapter.showRetry(show, errorMsg);
    }

    @Override
    public void clear() {
        adapter.clear();
        adapter.showRetry(false, "");
        currentPage = 1;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    isLoading = true;
                    currentPage += 1;

                    presenter.loadMore(currentPage);
                }
            }
        }
    };
}
