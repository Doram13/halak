package com.Doram;

import java.util.Random;

public class Almond extends Plant implements SpecialAttributes{
    private int plantCounter = 0;
    private int production;
    String name;
    private int defaultProductionIncrementation;

    Almond() {
        super();
        production = 50;
        name = "Almond" + plantCounter;
        plantCounter++;
    }

    @Override
    int produceFood() {
        if (production < 40) {
            production *= 2;
            System.out.println(name + "'s production has fallen below 40, so it's doubled!");
        }
        return production;
    }

    @Override
    public void growShroom() {
        production += 10;
        System.out.println(name + " has grown Shrooms, adding 10 to production. Current production is: " + this.production);
    }

    @Override
   public void rot() {
        production -= 18;
        if (production < 0 ) {production = 0;}
        System.out.println(name + " is rotting, reducing the production by 18. Current production is: " + this.production);
    }

    @Override
    void incrementProductionByDefault() {
        production += this.defaultProductionIncrementation;
    }

    @Override
    int passOneMonth() {
        Random r = new Random();
        if (r.nextDouble() <= 0.08) {
            growShroom();
        }
        r = new Random();
        if (r.nextDouble() <= 0.13) {
            rot();
            return 0;
        }
        incrementProductionByDefault();
        System.out.println("I'm " + this.name + " I've produced: " + production);
        return produceFood();
    }


}
