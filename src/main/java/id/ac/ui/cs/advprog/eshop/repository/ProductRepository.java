package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductid() == null) {
            product.setProductid(UUID.randomUUID());
        }
        productData.add(product);
        return product;
    }

    public Product edit(Product product) {
        Product existingProduct = findById(product.getProductid());

        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductQuantity(product.getProductQuantity());
            return existingProduct;
        }
        return null;
    }

        public Product findById(UUID id) {
        return productData.stream().filter(p -> p.getProductid().equals(id)).findFirst().orElse(null);
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

}
