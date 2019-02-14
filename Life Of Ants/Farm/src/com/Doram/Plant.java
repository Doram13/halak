package com.Doram;

public abstract class Plant {
    protected int production;
    private int DefaultProductionIncrementation = 4;
    int maxProduction;
    String name;


    void incrementProductionByDefault() {
        production += DefaultProductionIncrementation;
        System.out.println(name + "'s production has been incremented by 4, by default.");
    }

    abstract int produceFood();

    abstract int passOneMonth();
}
