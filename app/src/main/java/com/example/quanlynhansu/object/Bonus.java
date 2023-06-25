package com.example.quanlynhansu.object;

public class Bonus {
    private int bonusId;
    private double condition;
    private double bonus;

    public Bonus(int bonusId, double condition, double bonus) {
        this.bonusId = bonusId;
        this.condition = condition;
        this.bonus = bonus;
    }
    public Bonus( double condition, double bonus) {

        this.condition = condition;
        this.bonus = bonus;
    }
    public int getBonusId() {
        return bonusId;
    }

    public double getCondition() {
        return condition;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonusId(int bonusId) {
        this.bonusId = bonusId;
    }

    public void setCondition(double condition) {
        this.condition = condition;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
