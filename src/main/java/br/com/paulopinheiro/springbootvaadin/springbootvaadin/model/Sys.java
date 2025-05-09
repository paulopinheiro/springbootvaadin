package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.io.Serializable;
import java.sql.Timestamp;

public final class Sys implements Serializable {
    private final Integer type;
    private final Integer id;
    private final String country;
    private final Timestamp sunrise;
    private final Timestamp sunset;

    public Sys(Integer type, Integer id, String country, Timestamp sunrise, Timestamp sunset) {
        this.type = type;
        this.id = id;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Integer getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Timestamp getSunrise() {
        return sunrise;
    }

    public Timestamp getSunset() {
        return sunset;
    }
}
