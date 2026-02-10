package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Sampo Lama");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Baru");
        updatedProduct.setProductQuantity(20);
        productRepository.edit(updatedProduct);

        Product result = productRepository.findById(product.getProductId());
        assertEquals("Sampo Baru", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Sampo");
        productRepository.create(product);

        Product unknownProduct = new Product();
        unknownProduct.setProductId(UUID.randomUUID());
        unknownProduct.setProductName("Gagal");

        Product result = productRepository.edit(unknownProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Hapus Aku");
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        Product result = productRepository.findById(product.getProductId());
        assertNull(result);
    }

    @Test
    void testDeleteProductNegative() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Tetap Ada");
        productRepository.create(product);

        productRepository.delete(UUID.randomUUID());

        Product result = productRepository.findById(product.getProductId());
        assertNotNull(result);
        assertEquals("Tetap Ada", result.getProductName());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }
}