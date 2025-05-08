package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.io.Serializable;

public final class Main implements Serializable {
    private final Integer temp;
    private final Integer pressure;
    private final Integer humidity;
    private final Integer temp_min;
    private final Integer temp_max;

    public Integer getTemp() {
        return temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getTemp_min() {
        return temp_min;
    }

    public Integer getTemp_max() {
        return temp_max;
    }

    public Main(Integer temp, Integer pressure, Integer humidity, Integer temp_min, Integer temp_max) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

}
