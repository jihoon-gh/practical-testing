package example.cafekiosk.spring.api.controller;

import example.cafekiosk.spring.api.controller.product.ProductController;
import example.cafekiosk.spring.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired private MockMvc mockMvc;

    // @Mock도 있음.
    @MockBean
    private ProductService productService;

}
