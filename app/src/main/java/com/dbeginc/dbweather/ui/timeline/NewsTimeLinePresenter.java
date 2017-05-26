package com.dbeginc.dbweather.ui.timeline;

import com.crashlytics.android.Crashlytics;
import com.dbeginc.dbweather.models.datatypes.news.Article;
import com.dbeginc.dbweather.models.provider.AppDataProvider;
import com.dbeginc.dbweather.models.provider.schedulers.RxSchedulersProvider;
import com.dbeginc.dbweather.utils.utility.weather.WeatherUtil;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Bitsy Darel on 14.05.17.
 * News TimeLine Presenter
 */

class NewsTimeLinePresenter {

    private final AppDataProvider dataProvider;
    private final CompositeDisposable rxSubscription;
    private INewsTimeLineView view;
    private final RxSchedulersProvider schedulersProvider = RxSchedulersProvider.getInstance();
    private final PublishSubject<String> detailsEvent = PublishSubject.create();

    NewsTimeLinePresenter(@Nonnull final INewsTimeLineView view, @Nonnull final AppDataProvider dataProvider,
                          @Nonnull final CompositeDisposable compositeDisposable) {
        this.view = view;
        this.dataProvider = dataProvider;
        rxSubscription = compositeDisposable;
        rxSubscription.add(detailsEvent.subscribe(view::showDetails, Crashlytics::logException));
    }

    void subscribeToPublisher() {
        rxSubscription.add(detailsEvent.subscribe(view::showDetails, Crashlytics::logException));
    }

    void loadNews() {
        rxSubscription.add(dataProvider.getNewsFromDatabase()
                .subscribeOn(schedulersProvider.getNewsScheduler())
                .observeOn(schedulersProvider.getUIScheduler())
                .subscribeWith(new DisposableSingleObserver<List<Article>>() {
                    @Override
                    public void onSuccess(final List<Article> articles) { view.showNewsFeed(articles); }

                    @Override
                    public void onError(final Throwable throwable) {
                        Crashlytics.logException(throwable);
                        view.showError(throwable);
                    }
                }));
    }

    void getNews() {
        rxSubscription.add(dataProvider.getNewsFromDatabase()
                .subscribeOn(schedulersProvider.getNewsScheduler())
                .observeOn(schedulersProvider.getUIScheduler())
                .subscribeWith(new DisposableSingleObserver<List<Article>>() {
                    @Override
                    public void onSuccess(final List<Article> articles) { view.showNewsFeed(articles); }

                    @Override
                    public void onError(final Throwable throwable) {
                        Crashlytics.logException(throwable);
                        view.showError(throwable);
                    }
                }));
    }

    void getWeatherForBackHome() {
        rxSubscription.add(dataProvider.getWeatherFromDatabase()
                .subscribeOn(schedulersProvider.getWeatherScheduler())
                .unsubscribeOn(schedulersProvider.getWeatherScheduler())
                .observeOn(schedulersProvider.getComputationThread())
                .map(weather -> WeatherUtil.parseWeather(weather, view.getContext()))
                .observeOn(schedulersProvider.getUIScheduler())
                .subscribe(view::addWeatherToHomeIntent, Crashlytics::logException));
    }

    PublishSubject<String> getDetailsEvent() { return detailsEvent; }
}