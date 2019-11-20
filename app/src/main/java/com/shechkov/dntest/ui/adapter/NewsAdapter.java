package com.shechkov.dntest.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.shechkov.dntest.R;
import com.shechkov.dntest.interfaces.PagintationAdapterCallback;
import com.shechkov.dntest.models.Article;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NEWS_ITEM_VIEW_TYPE = 0;
    private static final int LOADING_ITEM_VIEW_TYPE = 1;

    private List<Article> newsList;
    private PagintationAdapterCallback adapterCallback;
    private final RequestManager mGlide;
    private boolean isLoadingAdded = false;
    private String errorMsg;
    private boolean retryPageLoad = false;

    public NewsAdapter(RequestManager mGlide, PagintationAdapterCallback adapterCallback) {
        newsList = new ArrayList<>();
        this.mGlide = mGlide;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case NEWS_ITEM_VIEW_TYPE:
                View newsView = inflater.inflate(R.layout.item_news, parent, false);
                viewHolder = new NewsViewHolder(newsView);
                break;
            case LOADING_ITEM_VIEW_TYPE:
                View loadingView = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(loadingView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case NEWS_ITEM_VIEW_TYPE:
                ((NewsViewHolder) holder).bind(getItem(position));
                break;
            case LOADING_ITEM_VIEW_TYPE:
                ((LoadingViewHolder) holder).bind();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == newsList.size() - 1 && isLoadingAdded)
            return LOADING_ITEM_VIEW_TYPE;
        return NEWS_ITEM_VIEW_TYPE;
    }

    private Article getItem(int position) {
        return newsList.get(position);
    }

    public void add(Article article) {
        newsList.add(article);
        notifyItemInserted(newsList.size() - 1);
    }

    public void addAll(List<Article> newsResults) {
        newsList.addAll(newsResults);
        int size = newsList.size();
        notifyItemRangeInserted(size == 0 ? 0 : size - 1, newsResults.size());
    }

    public void remove(Article article) {
        int position = newsList.indexOf(article);
        if (position > -1) {
            newsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(null);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        if (getItemCount() > 0) {
            int position = newsList.size() - 1;

            if (getItem(position) == null) {
                newsList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void clear(){
        int size = newsList.size();
        newsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(newsList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageNews;
        private TextView titleText;
        private TextView dateText;
        private TextView descText;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageNews = itemView.findViewById(R.id.image_news);
            titleText = itemView.findViewById(R.id.text_title);
            dateText = itemView.findViewById(R.id.text_date);
            descText = itemView.findViewById(R.id.text_desc);
        }

        public void bind(Article article) {

            mGlide.load(article.getUrlToImage())
                    .apply(new RequestOptions().placeholder(new ColorDrawable(Color.parseColor("#26000000"))))
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .into(imageNews);

            titleText.setText(article.getTitle());
            dateText.setText(article.getPublishedAt());
            descText.setText(article.getDescription());
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        public void bind() {
            if (retryPageLoad) {

                mErrorLayout.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);

                mErrorTxt.setText(
                        errorMsg != null ?
                                errorMsg :
                                "ОШИБКА"); //TODO ИСПРАВИТЬ НА СТРОКОВЫЙ РЕСУРС

            } else {
                mErrorLayout.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    adapterCallback.retryPageLoad();

                    break;
            }
        }
    }

}
