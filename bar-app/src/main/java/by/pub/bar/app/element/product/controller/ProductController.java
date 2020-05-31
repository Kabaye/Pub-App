package by.pub.bar.app.element.product.controller;

import by.pub.bar.app.element.product.entity.Product;
import by.pub.bar.app.element.product.service.ProductService;
import by.pub.bar.app.web.client.converter.ProductConverter;
import by.pub.bar.app.web.client.dto.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    public ProductController(ProductService productService, ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAllProducts()
                .stream()
                .map(productConverter::toDTO)
                .collect(Collectors.toList());
    }

}
