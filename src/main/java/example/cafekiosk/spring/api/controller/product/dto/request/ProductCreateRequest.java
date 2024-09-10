package example.cafekiosk.spring.api.controller.product.dto.request;

import example.cafekiosk.spring.domain.product.Product;
import example.cafekiosk.spring.domain.product.ProductSellingStatus;
import example.cafekiosk.spring.domain.product.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;

    @NotNull(message = "상품 판매 상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    @Positive(message = "상품 가격은 0 이상이어야 합니다.")
    private int price;

    @NotBlank(message = "상품 이름은 필수입니다.")
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
