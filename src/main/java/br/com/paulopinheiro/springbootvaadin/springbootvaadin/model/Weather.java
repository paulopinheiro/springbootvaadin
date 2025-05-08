package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.io.Serializable;

public final class Weather implements Serializable {
    private final Integer id;
    private final String main;
    private final String description;
    private final String icon;

    public Weather(Integer id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
