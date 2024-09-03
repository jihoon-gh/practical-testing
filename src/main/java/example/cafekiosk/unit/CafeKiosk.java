package example.cafekiosk.unit;

import example.cafekiosk.unit.beverage.Beverage;
import example.cafekiosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime SHOT_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverageList = new ArrayList<>();

    public void add(Beverage beverage) {
        beverageList.add(beverage);
    }
    public void add(Beverage beverage, int count) {
        if(count <= 0){
            throw new IllegalArgumentException("음료는 한 잔 이상 주문 가능");
        }
        for(int i = 0; i < count; i++) {
            beverageList.add(beverage);
        }
    }
    public void delete(Beverage beverage){
        beverageList.remove(beverage);
    }

    public void clear(){
        beverageList.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for(Beverage b : beverageList){
            totalPrice += b.getPrice();
        }
        return totalPrice;
    }

    public Order createOrder(LocalDateTime currentDateTime){

        LocalTime currentTime = currentDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOT_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }

        return new Order(currentDateTime, beverageList);
    }
}
