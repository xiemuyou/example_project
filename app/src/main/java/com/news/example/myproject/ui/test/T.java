package com.news.example.myproject.ui.test;

import java.util.Objects;

/**
 * @author xiemy2
 * @date 2019/7/2
 */
public class T {
    private int id;
    private String name;

    public T(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "T{" +
                "it='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof T && ((T) o).getId() == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
