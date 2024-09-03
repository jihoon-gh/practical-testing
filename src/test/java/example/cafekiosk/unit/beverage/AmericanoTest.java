package example.cafekiosk.unit.beverage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getNameTest(){
        Americano americano = new Americano();

        assertThat(americano.getName()).isEqualTo("americano");
    }

    @Test
    void getPriceTest(){
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4000);
    }

}