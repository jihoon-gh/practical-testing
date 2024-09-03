package example.cafekiosk.unit.beverage;

import example.cafekiosk.unit.beverage.Beverage;

public class Latte implements Beverage {
    @Override
    public int getPrice() {
        return 5000;
    }

    @Override
    public String getName() {
        return "Latte";
    }
}
