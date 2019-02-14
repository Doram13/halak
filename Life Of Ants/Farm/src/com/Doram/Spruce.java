package com.Doram;

import java.util.Random;

public class Spruce extends EverGreen implements SpecialAttributes{
    private int plantCounter = 0;
    private int production;
    String name;
    int monthsOld;
    int defaultProductionIncrementation;


    Spruce() {
        super();
        this.production = 26;
        name = "Spruce" + plantCounter;
        plantCounter++;
    }

    @Override
    int produceFood() {
        return production;
    }

    @Override
    public void growShroom() {
        production += 15;
        System.out.println(name + " has grown Shrooms, adding 15 to production. Current production is: " + this.production);
    }

    @Override
    public void rot() {
        production -= 20;
        if (production < 0 ) {production = 0;}
        System.out.println(name + " is rotting, reducing the production by 20. Current production is: " + this.production);
    }

    @Override
     void incrementProductionByDefault() {
        production += defaultProductionIncrementation;
    }

    @Override
    int passOneMonth() {
        Random r = new Random();
        if (r.nextDouble() <= 0.05) {
            growShroom();
        }
        r = new Random();
        if (r.nextDouble() <= 0.04) {
            rot();
            return 0;
        }
        if (this.monthsOld % 5 == 0) {
            this.growLeaves();
        }
        else {this.incrementProductionByDefault();}
        if (production >= this.maxProduction) {
            this.speak();
            production = maxProduction;
        }
        System.out.println("I'm " + this.name + " I've produced: " + production);
        monthsOld++;
        return produceFood();
    }
}
