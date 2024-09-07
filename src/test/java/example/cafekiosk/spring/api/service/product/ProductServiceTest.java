package example.cafekiosk.spring.api.service.product;

import example.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import example.cafekiosk.spring.api.service.product.response.ProductResponse;
import example.cafekiosk.spring.domain.product.Product;
import example.cafekiosk.spring.domain.product.ProductRepository;
import example.cafekiosk.spring.domain.product.ProductSellingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static example.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static example.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품 번호에서 1 증가한 값이다.")
    @Test
    void createProduct(){
        //given
        Product product1 = createProduct("001", SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HOLD, "카페라뗴", 4500);
        Product product3 = createProduct("003", STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        //when
        ProductResponse productResponse = productService.createProduct(request);
        //then
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
