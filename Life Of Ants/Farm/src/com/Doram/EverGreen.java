package com.Doram;

public abstract class EverGreen extends Plant {
    protected static final int maxProduction = 70;
    int monthsOld;

    void speak() {
        System.out.println(this.name + " at full production!");
    }

    void growLeaves() {
        this.production += 8;
        System.out.println("I'm " + this.name + " And I grow leaves, addig 8 to production!" );
    }


}
