package by.pub.bar.app.product.service;

import by.pub.bar.app.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findByName(String name);

    Product findById(String id);

    Product saveProduct(Product product);

    void deleteProductByName(String name);
}
