package com.Doram;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    int totalProduction;
    int monthsPassed;
    List<Plant> plantList = new ArrayList<>();

    Farm() {
        for (int i = 0; i <= 1 ; i++) {
            Spruce spruce = new Spruce();
            plantList.add(spruce);
            Almond almond = new Almond();
            plantList.add(almond);
            Juniper juniper = new Juniper();
            plantList.add(juniper);
        }
    }

    void simulateAMonth() {
        for (Plant plant: plantList) {
            totalProduction += plant.passOneMonth();
            System.out.println("Total food produced until now: " + totalProduction);
        }
    }
}
