package com.shechkov.dntest.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shechkov.dntest.BaseApp;
import com.shechkov.dntest.R;
import com.shechkov.dntest.di.component.ApplicationComponent;
import com.shechkov.dntest.interfaces.PagintationAdapterCallback;
import com.shechkov.dntest.models.News;
import com.shechkov.dntest.ui.adapter.NewsAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout errorLayout;

    public MainPresenterImpl presenter;

    private int TOTAL_PAGES = 5;
    private boolean isLoading = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.rv_news);
        swipeRefreshLayout = findViewById(R.id.refresh_list);
        errorLayout = findViewById(R.id.error_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));

        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new NewsAdapter(Glide.with(this), new PagintationAdapterCallback() {
            @Override
            public void retryPageLoad() {
                presenter.loadMore(currentPage);
            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading)
                {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)
                    {
                        isLoading = true;
                        currentPage += 1;

                        presenter.loadMore(currentPage);
                    }
                }
            }
        });

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

        presenter = new MainPresenterImpl(getApplication());
        presenter.attachView(this);
        presenter.loadData();
    }

    @Override
    public void setData(News news) {
        adapter.removeLoadingFooter();
        isLoading = false;
        adapter.addAll(news.getArticles());

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
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
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
}
