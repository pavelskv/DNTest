package com.shechkov.dntest.ui.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.shechkov.dntest.R;
import com.shechkov.dntest.model.Article;

public class DetailsActivity extends AppCompatActivity {

    private static final String NEWS_ITEM = "NEWS_ITEM";
    private ProgressBar progressBar;

    private WebView webView;

    public static Intent newIntent(Context packageContext, Article article) {
        Intent intent = new Intent(packageContext, DetailsActivity.class);
        intent.putExtra(NEWS_ITEM, article);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Article currentItem = getIntent().getParcelableExtra(NEWS_ITEM);
        setTitle(currentItem.getTitle());

        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_view_news);
        webView.loadUrl(currentItem.getUrl());
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress >= 70)
                    progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else finish();
    }
}
