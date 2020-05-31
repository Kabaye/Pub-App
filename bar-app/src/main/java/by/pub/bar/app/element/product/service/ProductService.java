package by.pub.bar.app.element.product.service;

import by.pub.bar.app.element.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findByName(String name);

    Product saveProduct(Product product);
}
