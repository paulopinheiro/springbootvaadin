package br.com.paulopinheiro.springbootvaadin.springbootvaadin.model;

import java.io.Serializable;

public final class Clouds implements Serializable {
    private final Integer all;

    public Clouds(Integer all) {
        this.all = all;
    }

    public Integer getAll() {
        return all;
    }
}
