package com.darelbitsy.dbweather.models.datatypes.weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Darel Bitsy on 19/02/17.
 */

public class HourlyData implements Parcelable {
    @SerializedName("time")
    @Expose
    private long time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("precipIntensity")
    @Expose
    private double precipIntensity;
    @SerializedName("precipProbability")
    @Expose
    private double precipProbability;
    @SerializedName("precipType")
    @Expose
    private String precipType;
    @SerializedName("temperature")
    @Expose
    private double temperature;
    @SerializedName("apparentTemperature")
    @Expose
    private double apparentTemperature;
    @SerializedName("dewPoint")
    @Expose
    private double dewPoint;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("windSpeed")
    @Expose
    private double windSpeed;
    @SerializedName("windBearing")
    @Expose
    private long windBearing;
    @SerializedName("visibility")
    @Expose
    private double visibility;
    @SerializedName("cloudCover")
    @Expose
    private double cloudCover;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("ozone")
    @Expose
    private double ozone;


    /**
     * Empty constructor to initiate the class
     * and populate it later
     */
    public HourlyData() {}

    protected HourlyData(Parcel in) {
        time = in.readLong();
        summary = in.readString();
        icon = in.readString();
        precipIntensity = in.readDouble();
        precipProbability = in.readDouble();
        precipType = in.readString();
        temperature = in.readDouble();
        apparentTemperature = in.readDouble();
        dewPoint = in.readDouble();
        humidity = in.readDouble();
        windSpeed = in.readDouble();
        windBearing = in.readLong();
        visibility = in.readDouble();
        cloudCover = in.readDouble();
        pressure = in.readDouble();
        ozone = in.readDouble();
    }

    public static final Creator<HourlyData> CREATOR = new Creator<HourlyData>() {
        @Override
        public HourlyData createFromParcel(Parcel in) {
            return new HourlyData(in);
        }

        @Override
        public HourlyData[] newArray(int size) {
            return new HourlyData[size];
        }
    };

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon == null ? "clear-day" : icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(final double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(final double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(final String precipType) {
        this.precipType = precipType;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(final double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(final double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(final double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(final double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }


    public void setWindSpeed(final double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public long getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(final long windBearing) {
        this.windBearing = windBearing;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(final double visibility) {
        this.visibility = visibility;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(final double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(final double pressure) {
        this.pressure = pressure;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(final double ozone) {
        this.ozone = ozone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(summary);
        dest.writeString(icon);
        dest.writeDouble(precipIntensity);
        dest.writeDouble(precipProbability);
        dest.writeString(precipType);
        dest.writeDouble(temperature);
        dest.writeDouble(apparentTemperature);
        dest.writeDouble(dewPoint);
        dest.writeDouble(humidity);
        dest.writeDouble(windSpeed);
        dest.writeLong(windBearing);
        dest.writeDouble(visibility);
        dest.writeDouble(cloudCover);
        dest.writeDouble(pressure);
        dest.writeDouble(ozone);
    }
}
