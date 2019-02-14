package com.Doram;

public class Juniper extends EverGreen {
    private int plantCounter = 0;
    private int production;
    String name;
    int monthsOld;
   // private int maxProduction;

    Juniper() {
        super();
        production = 14;
        name = "Juniper" + plantCounter;
        plantCounter++;
        //this.maxProduction = EverGreen.maxProduction;
    }

    @Override
    int produceFood() {
        return production;
    }

    @Override
    int passOneMonth() {
        if (this.monthsOld % 5 == 0) {
            this.growLeaves();
        }
        this.incrementProductionByDefault();
        if (production >= this.maxProduction) {
            this.speak();
            production = maxProduction;
        }
        System.out.println("I'm " + this.name + " I've produced: " + production);
        monthsOld++;
        return produceFood();
    }
}
