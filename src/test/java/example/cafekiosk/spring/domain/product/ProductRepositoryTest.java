package example.cafekiosk.spring.domain.product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static example.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static example.cafekiosk.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

//@DataJpaTest는 Transactional이 달려있음. data에 Transactional 달면 rollback됨
@DataJpaTest //JPA 관련 빈들만
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매 상태를 가진 상품 조회")
    @Test
    void findAllBySellingStatusIn(){
        //given
        Product product1 = createProduct("001", SELLING, "아메리카노", 4000);

        Product product2 = createProduct("002", HOLD, "카페라떼", 4500);

        Product product3 = createProduct("001", STOP_SELLING, "팥빙수", 7000);
        
        productRepository.saveAll(List.of(product1, product2, product3));
        
        //when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        //then
        Assertions.assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품 번호를 가진 상품 조회")
    @Test
    void findAllByProductNumberIn(){
        //given
        Product product1 = createProduct("001", SELLING, "아메리카노", 4000);

        Product product2 = createProduct("002", HOLD, "카페라뗴", 4500);

        Product product3 = createProduct("003", STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라뗴", HOLD)
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 읽어온다.")
    @Test
    void findLatestProductNumber() {
        //given
        Product product1 = createProduct("001", SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HOLD, "카페라뗴", 4500);
        String targetProductNumber = "003";
        Product product3 = createProduct(targetProductNumber, STOP_SELLING, "팥빙수", 7000);

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        String latestProductNumber = productRepository.findLatestProductNumber();

        //then
        assertThat(latestProductNumber).isEqualTo(targetProductNumber);
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우 Null 반환")
    @Test
    void findLatestProductNumberWhenProductIsEmpty() {
        //given
        //when
        String latestProductNumber = productRepository.findLatestProductNumber();

        //then
        assertThat(latestProductNumber).isNull();
    }

    private static Product createProduct(String targetProductNumber, ProductSellingStatus stopSelling, String productName, int price) {
        return Product.builder()
                .productNumber(targetProductNumber)
                .type(HANDMADE)
                .sellingStatus(stopSelling)
                .name(productName)
                .price(price)
                .build();
    }

}
