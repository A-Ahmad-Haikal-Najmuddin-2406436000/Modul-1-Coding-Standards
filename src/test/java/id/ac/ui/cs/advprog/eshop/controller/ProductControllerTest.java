package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    private static final String PRODUCT_LIST_URL = "/product/list";
    private Product product;

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(67);
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testPostCreateProduct() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productId", product.getProductId().toString())
                        .param("productName", product.getProductName())
                        .param("productQuantity", String.valueOf(product.getProductQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service, times(1)).create(any(Product.class));
    }

    @Test
    void testCreateProductFailEmptyName() throws Exception {
        product.setProductName("");
        product.setProductQuantity(67);
        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("product", "productName"));
        verify(service, times(0)).create(any(Product.class));
    }

    @Test
    void testCreateProductPostFailZeroQuantity() throws Exception {
        product.setProductQuantity(0);
        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("product", "productQuantity"));
        verify(service, times(0)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> allProducts = Arrays.asList(product1, product2);

        when(service.findAll()).thenReturn(allProducts);

        mockMvc.perform(get(PRODUCT_LIST_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attribute("products", allProducts));
    }

    @Test
    void testEditProductPage() throws Exception {
        when(service.findById(product.getProductId())).thenReturn(product);

        mockMvc.perform(get("/product/edit/" + product.getProductId()))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));

        verify(service, times(1)).findById(product.getProductId());
    }

    @Test
    void testEditProductPostFailEmptyName() throws Exception {
        product.setProductName("");
        mockMvc.perform(post("/product/edit")
                        .flashAttr("product", product))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("product", "productName"));

        verify(service, times(0)).edit(any(Product.class));
    }

    @Test
    void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", product.getProductId().toString())
                        .param("productName", "Sampo Cap Usep")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service, times(1)).edit(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/" + product.getProductId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("../list"));

        verify(service, times(1)).delete(product.getProductId());
    }
}