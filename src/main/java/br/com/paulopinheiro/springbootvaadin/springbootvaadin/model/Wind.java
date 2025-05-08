package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.io.Serializable;

public final class Wind implements Serializable {
    private final Double speed;
    private final Double deg;

    public Wind(Double speed, Double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getDeg() {
        return deg;
    }
}
