package example.cafekiosk.spring.api.controller.product;

import example.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import example.cafekiosk.spring.api.service.product.ProductService;
import example.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request){
        return productService.createProduct(request);
    }

    @GetMapping("api/v1/products/selling")
    public List<ProductResponse> getSellingProducts(){
        return productService.getSellingProducts();
    }

}
