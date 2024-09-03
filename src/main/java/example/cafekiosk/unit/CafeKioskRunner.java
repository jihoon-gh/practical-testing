package example.cafekiosk.unit;

import example.cafekiosk.unit.beverage.Americano;
import example.cafekiosk.unit.beverage.Latte;

public class CafeKioskRunner {
    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println(">>>>americano");

        cafeKiosk.add(new Latte());
        System.out.println(">>>>latte");

        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("totalPrice = " + totalPrice);
    }
}
