package example.cafekiosk.unit;

import example.cafekiosk.unit.beverage.Americano;
import example.cafekiosk.unit.order.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_manual_test(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>>담긴 음료 수 : " + cafeKiosk.getBeverageList().size());
        System.out.println(">>>담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
    }

    @Test
    void addTest(){

        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("americano");
    }

    @Test
    void addSeveralBeveragesTest(){

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();


        cafeKiosk.add(americano, 2);


        assertThat(cafeKiosk.getBeverageList().get(1).getName()).isEqualTo("americano");
        assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("americano");
    }

    @Test
    void addZeroBeveragesTest(){

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();



        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 한 잔 이상 주문 가능");
    }

    @Test
    void removeTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        cafeKiosk.delete(americano);

        assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    void createOrderTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);


        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 22, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");

    }

}