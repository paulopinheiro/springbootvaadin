package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.sql.Timestamp;
import java.util.List;

public final class WeatherReport {
    private final Coord coord;
    private final List<Weather> weatherList;
    private final String base;
    private final Main main;
    private final Integer visibility;
    private final Wind wind;
    private final Clouds clouds;
    private final Timestamp dt;
    private final Sys sys;
    private final Integer id;
    private final String name;
    private final Integer cod;

    public WeatherReport(Coord coord, List<Weather> weatherList, String base, Main main, Integer visibility, Wind wind, Clouds clouds, Timestamp dt, Sys sys, Integer id, String name, Integer cod) {
        this.coord = coord;
        this.weatherList = weatherList;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Timestamp getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCod() {
        return cod;
    }
}
