package com.dbeginc.dbweather.ui.main.config;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.dbeginc.dbweather.models.datatypes.geonames.GeoName;

import java.util.List;

/**
 * Created by darel on 11.06.17.
 */

public interface IConfigurationView {
    void loadCities(@NonNull final List<GeoName> geoNames);

    void respondToClick(final Pair<Integer, Boolean> clickEvent);
}
