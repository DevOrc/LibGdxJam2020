package com.devorc.gdxjam.robot;

import com.devorc.gdxjam.Item;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class RobotStats {

    final RobotStat laserLevel = new RobotStat("Mining Laser", Item.RUBY, 1,
            this::calcLaserTime, exponentialCost(2, 4));
    final RobotStat shields = new RobotStat("Shield", Item.SAPPHIRE, 0,
            this::calcShieldStrength, exponentialCost(2, 4));
    final RobotStat turret = new RobotStat("Turret", Item.PCB, 1,
            this::calcTurretTime, exponentialCost(3, 4));


    private final List<RobotStat> stats = Arrays.asList(laserLevel, turret, shields);

    private Function<Integer, Integer> exponentialCost(int base, int max){
        return level -> {
            if(level > max){
                return Integer.MAX_VALUE;
            }

            return 2 * (int) Math.pow(base, level);
        };
    }

    private int calcTurretTime(int level) {
        return 18 - (3 * level);
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

    public void maxAll() {
        stats.forEach(stat -> {
            while(stat.getCost() != Integer.MAX_VALUE){
                stat.onUpgrade();
            }
        });
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
