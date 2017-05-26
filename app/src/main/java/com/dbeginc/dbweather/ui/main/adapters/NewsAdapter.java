package com.dbeginc.dbweather.ui.main.adapters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.dbeginc.dbweather.R;
import com.dbeginc.dbweather.databinding.NewsListItemBinding;
import com.dbeginc.dbweather.models.datatypes.news.Article;
import com.dbeginc.dbweather.models.provider.schedulers.RxSchedulersProvider;
import com.dbeginc.dbweather.ui.newsdetails.NewsDialogActivity;
import com.dbeginc.dbweather.utils.helper.ArticleDiffCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

import static com.dbeginc.dbweather.utils.holder.ConstantHolder.INDEX;
import static com.dbeginc.dbweather.utils.holder.ConstantHolder.NEWS_DATA_KEY;
import static com.dbeginc.dbweather.utils.holder.ConstantHolder.NEWS_FEED;

/**
 * Created by Darel Bitsy on 16/02/17.
 * News Adapter for the news recycler view
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>  {
    private final List<Article> mNewses = new ArrayList<>();
    private final RxSchedulersProvider rxSchedulersProvider = RxSchedulersProvider.getInstance();
    private CompositeDisposable compositeDisposable;

    public NewsAdapter(final List<Article> newses, final CompositeDisposable compositeDisposable) {
        mNewses.addAll(newses);
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(final ViewGroup parent,
                                             final int viewType) {

        return new NewsViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.news_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) { holder.bindNews(mNewses.get(position)); }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position, final List<Object> payloads) {
        for (final Object object : payloads) {
            final Bundle bundle = (Bundle) object;
            mNewses.add(bundle.getInt(INDEX), bundle.getParcelable(NEWS_DATA_KEY));
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mNewses.size();
    }

    public void updateContent(final List<Article> newses) {
        compositeDisposable.add(Single.fromCallable(() -> DiffUtil.calculateDiff(new ArticleDiffCallback(mNewses, newses)))
                .subscribeOn(rxSchedulersProvider.getComputationThread())
                .observeOn(rxSchedulersProvider.getUIScheduler())
                .subscribeWith(new DisposableSingleObserver<DiffUtil.DiffResult>() {
                    @Override
                    public void onSuccess(final DiffUtil.DiffResult diffResult) {
                        diffResult.dispatchUpdatesTo(NewsAdapter.this);
                    }
                    @Override
                    public void onError(final Throwable throwable) { Crashlytics.logException(throwable); }
                }));
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private final NewsListItemBinding newsContainer;
        Article mNews;

        final View.OnClickListener newsOnClickListener = view -> {

            final Intent newsActivity = new Intent(view.getContext(), NewsDialogActivity.class);
            newsActivity.putExtra(NEWS_DATA_KEY, mNews);
            Answers.getInstance().logCustom(new CustomEvent(NEWS_FEED)
            .putCustomAttribute("OPENED", 1));
            view.getContext().startActivity(newsActivity);
        };

        NewsViewHolder(final NewsListItemBinding newsListItemBinding) {
            super(newsListItemBinding.getRoot());
            newsContainer = newsListItemBinding;
            newsContainer.newsLayout.setOnClickListener(newsOnClickListener);
        }

        void bindNews(final Article news) {
            mNews = news;
            newsContainer.setArticle(news);
            if (news.getAuthor().contains("sport")) { newsContainer.newsFrom.setBackgroundColor(Color.BLUE); }
            else { newsContainer.newsFrom.setBackgroundColor(Color.RED); }
            newsContainer.executePendingBindings();
        }
    }
}