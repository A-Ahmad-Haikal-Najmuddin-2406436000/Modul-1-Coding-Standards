package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository implements BaseRepository<Product, UUID>{
    private List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(UUID.randomUUID());
        }
        productData.add(product);
        return product;
    }

    @Override
    public Product edit(Product product) {
        Product existingProduct = findById(product.getProductId());

        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductQuantity(product.getProductQuantity());
            return existingProduct;
        }
        return null;
    }

    @Override
    public Product findById(UUID id) {
        return productData.stream().filter(p -> p.getProductId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void delete(UUID id) {
        productData.removeIf(p -> p.getProductId().equals(id));
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

}
