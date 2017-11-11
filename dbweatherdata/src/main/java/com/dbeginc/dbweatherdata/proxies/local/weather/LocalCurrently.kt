/*
 *  Copyright (C) 2017 Darel Bitsy
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dbeginc.dbweatherdata.proxies.local.weather

import android.arch.persistence.room.ColumnInfo
import android.support.annotation.RestrictTo

/**
 * Created by darel on 16.09.17.
 *
 * Local Currently Object
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
data class LocalCurrently(@ColumnInfo val time: Long, @ColumnInfo val summary: String, @ColumnInfo val icon: String, @ColumnInfo val temperature: Double,
                          @ColumnInfo val apparentTemperature: Double?, @ColumnInfo val precipIntensity: Double, @ColumnInfo val precipIntensityError: Double?, @ColumnInfo val precipProbability: Double,
                          @ColumnInfo val precipType: String?, @ColumnInfo val nearestStormDistance: Long?, @ColumnInfo val nearestStormBearing: Long?, @ColumnInfo val humidity: Double,
                          @ColumnInfo val windSpeed: Double, @ColumnInfo val cloudCover: Double, @ColumnInfo val windBearing: Long?, @ColumnInfo val visibility: Double,
                          @ColumnInfo val dewPoint: Double?, @ColumnInfo val pressure: Double
)