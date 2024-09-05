package example.cafekiosk.spring.api.service.order;

import example.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import example.cafekiosk.spring.api.service.order.response.OrderResponse;
import example.cafekiosk.spring.domain.order.Order;
import example.cafekiosk.spring.domain.order.OrderRepository;
import example.cafekiosk.spring.domain.product.Product;
import example.cafekiosk.spring.domain.product.ProductRepository;
import example.cafekiosk.spring.domain.product.ProductType;
import example.cafekiosk.spring.domain.stock.Stock;
import example.cafekiosk.spring.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        deductStockQuantities(products);


        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantities(List<Product> products) {
        // 재고 차감 체크가 필요한 상품 필터링
        List<String> stockProductNumbers = extractStockProductNumbers(products);

        // 재고 엔티티 조회
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        // 상품별 counting
        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        // 재고 차감 시도
        for(String stockProductNumber : new HashSet<>(stockProductNumbers)){
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if(stock.isQuantityLessThan(quantity)){ // 이 체크는 서비스에서의 체크
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            // stock은 서비스의 수량 체크와 관계 없이 스스로 수행.
            // 예외 메세지가 다른 이유가 있다.
            stock.deductQuantity(quantity);
        }
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private static List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
                .filter(p -> ProductType.containsStockType(p.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}
