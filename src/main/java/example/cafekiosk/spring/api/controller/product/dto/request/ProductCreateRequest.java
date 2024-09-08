package example.cafekiosk.spring.api.controller.product.dto.request;

import example.cafekiosk.spring.domain.product.Product;
import example.cafekiosk.spring.domain.product.ProductSellingStatus;
import example.cafekiosk.spring.domain.product.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private int price;
    private String name;

    @Builder
    private ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, int price, String name) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.price = price;
        this.name = name;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
