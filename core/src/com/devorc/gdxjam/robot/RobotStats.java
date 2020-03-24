package com.devorc.gdxjam.robot;

import com.devorc.gdxjam.Item;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class RobotStats {

    final RobotStat laserLevel =
            new RobotStat("Mining Laser", Item.RUBY, 1, this::calcLaserTime, exponentialCost(4));
    final RobotStat shields =
            new RobotStat("Shield", Item.PCB, 0, this::calcShieldStrength, l -> 0);

    final RobotStat engines =
            new RobotStat("TBD", Item.ROCK, 0, this::linear, this::linear);

    private final List<RobotStat> stats = Arrays.asList(laserLevel, engines, shields);

    private Function<Integer, Integer> exponentialCost(int max){
        return level -> {
            if(level > max){
                return Integer.MAX_VALUE;
            }

            return 2 * (int) Math.pow(2, level);
        };
    }

    private int calcLaserTime(int level){
        return 75 - (15 * level);
    }

    private int calcShieldStrength(int level) {
        return level * 100;
    }

    private int linear(int level){
        if(level > 2){
            return Integer.MAX_VALUE;
        }

        return level * 2;
    }

    List<RobotStat> getStats(){
        return stats;
    }

    class RobotStat{

        private final String name;
        private final Item requirement;

        private final Function<Integer, Number> calcValue;
        private final Function<Integer, Integer> calcCost;

        private int level;
        private Number value;
        private int cost;

        public RobotStat(String name, Item requirement, int level, Function<Integer, Number> calcValue, Function<Integer, Integer> calcCost) {
            this.name = name;
            this.requirement = requirement;
            this.calcValue = calcValue;
            this.calcCost = calcCost;
            this.level = level;

            this.value = calcValue.apply(level);
            this.cost = calcCost.apply(level);
        }

        public void onUpgrade(){
            level++;

            this.value = calcValue.apply(level);
            this.cost = calcCost.apply(level);


            System.out.println("Name: " + value);
        }

        public boolean isMaxedOut(){
            return cost == Integer.MAX_VALUE;
        }

        public Number getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public Item getRequirement() {
            return requirement;
        }

        public int getCost() {
            return cost;
        }
    }
}
