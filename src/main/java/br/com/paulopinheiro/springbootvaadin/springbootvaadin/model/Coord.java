package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.io.Serializable;

public final class Coord implements Serializable {
    private final Double lat;
    private final Double lon;

    public Coord(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
