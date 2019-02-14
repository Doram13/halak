package com.Doram;

public abstract class Plant {
    protected int production;
    private int defaultProductionIncrementation = 4;
    int maxProduction;
    String name;


     abstract void incrementProductionByDefault();

    abstract int produceFood();

    abstract int passOneMonth();
}
