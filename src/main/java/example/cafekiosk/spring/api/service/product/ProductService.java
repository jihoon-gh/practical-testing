package example.cafekiosk.spring.api.service.product;

import example.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import example.cafekiosk.spring.api.service.product.response.ProductResponse;
import example.cafekiosk.spring.domain.product.Product;
import example.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static example.cafekiosk.spring.domain.product.ProductSellingStatus.forDisplay;

@Service
@Transactional(readOnly = true)
/**
 * readOnly = true면 성능 향상 (JPA에서 CUD 스냅샷 저장, 변경감지 체크를 안할거니까)
 * CQRS -> 보통 read 요청이 많은데 command 가 read 때문에 동작 안하면 안됨
 * 그렇기 때문에, 조회용 서비스와 Command(CUD) 서비스를 구분해서 만들 수 있음
 * DB 엔드포인트를 아예 분리할 수도 있음(AWS RDS 등에서 Read-only instance를 두는데 이걸 구분하는걸 이용 가능함)
 */
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(){
        return productRepository.findAllBySellingStatusIn(forDisplay()).stream()
                .map(ProductResponse::of)
                .toList();
    }


    // 동시성 이슈 -> synchronized, redis lock, JPA Optimistic Lock ...
    //혹은 아예 Unique value 부여 ex: UUID
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();
        if(latestProductNumber == null){
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
