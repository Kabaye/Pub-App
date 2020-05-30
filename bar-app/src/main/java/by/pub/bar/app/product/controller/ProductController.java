package by.pub.bar.app.product.controller;

import by.pub.bar.app.product.entity.Product;
import by.pub.bar.app.product.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{productId}")
    public Product findProductById(@PathVariable String productId) {
        return productService.findById(productId);
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.findAllProducts();
    }

    @DeleteMapping("/{name}")
    public void deleteProductByName(@PathVariable String name) {
        productService.deleteProductByName(name);
    }
}
