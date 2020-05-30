package by.pub.bar.app.product.service;

import by.pub.bar.app.product.entity.Product;
import by.pub.bar.app.product.repository.ProductRepository;
import by.pub.bar.app.product.utils.ProductDBProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDBProcessor processor;

    public ProductServiceImpl(ProductRepository productRepository, ProductDBProcessor processor) {
        this.productRepository = productRepository;
        this.processor = processor;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(processor::fromDB)
                .collect(Collectors.toList());
    }

    @Override
    public Product findByName(String name) {
        return processor.fromDB(productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("There is no product with name: " + name)));
    }

    @Override
    public Product findById(String id) {
        return processor.fromDB(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no product with id: " + id)));
    }

    @Override
    public Product saveProduct(Product product) {
        return processor.fromDB(productRepository.save(processor.toDB(product)));
    }

    @Override
    public void deleteProductByName(String name) {
        productRepository.deleteByName(name);
    }
}
