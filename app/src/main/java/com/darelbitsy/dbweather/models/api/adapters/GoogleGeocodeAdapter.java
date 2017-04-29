package com.darelbitsy.dbweather.models.api.adapters;

import com.darelbitsy.dbweather.extensions.holder.ConstantHolder;
import com.darelbitsy.dbweather.extensions.utility.AppUtil;
import com.darelbitsy.dbweather.models.api.services.GoogleGeocodeService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Darel Bitsy on 20/02/17.
 * Class help to get user Location
 * if default geocode api false
 */

public class GoogleGeocodeAdapter {
    private static final String GOOGLE_GEOCODE_API_URL= "https://maps.googleapis.com/";
    private static final List<String> supportedLanguage = Arrays.asList(
            "ar", "bg", "bn", "ca", "cs", "da", "de",
            "el", "en", "en-AU", "en-GB", "es", "eu",
            "fa", "fi", "fil", "fr", "gl", "gu", "hi",
            "hr", "hu", "id", "it", "iw", "ja", "kn",
            "ko", "lt", "lv", "ml", "mr", "nl", "no",
            "pl", "pt", "pt-BR", "pt-PT", "ro", "ru",
            "sk", "sl", "sr", "sv", "ta", "te", "th",
            "tl", "tr", "uk", "zh-CN", "zh-TW"
        );
    private GoogleGeocodeService mGeocodeService;

    public GoogleGeocodeAdapter() {
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(GOOGLE_GEOCODE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(AppUtil.translateOkHttpClient)
                .build();

        mGeocodeService = restAdapter.create(GoogleGeocodeService.class);
    }

    public String getLocationByCoordinate(final double latitude,
                                          final double longitude) throws IOException {

        final String coordinates = String.format(Locale.ENGLISH, "%f,%f", latitude, longitude);

        if (supportedLanguage.contains(ConstantHolder.USER_LANGUAGE)) {
            return mGeocodeService
                    .getLocationNameWithLanguage(coordinates, ConstantHolder.USER_LANGUAGE)
                    .execute()
                    .body()
                    .getResults().get(0)
                    .getFormattedAddress();
        } else {
            return mGeocodeService
                    .getLocationName(coordinates)
                    .execute()
                    .body()
                    .getResults().get(0)
                    .getFormattedAddress();
        }
    }
}
