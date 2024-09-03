package example.cafekiosk.spring.api.service.product;

import example.cafekiosk.spring.api.service.product.response.ProductResponse;
import example.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static example.cafekiosk.spring.domain.product.ProductSellingStatus.forDisplay;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(){
        return productRepository.findAllBySellingStatusIn(forDisplay()).stream()
                .map(ProductResponse::of)
                .toList();
    }



}
