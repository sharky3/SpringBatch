package com.tryagain.batch.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Payment {
    @Id
    private long id;
    private String name;
    private double value;

    public Payment() {
    }

    public Payment(long id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", value=" + value + "]\n";
    }
}



