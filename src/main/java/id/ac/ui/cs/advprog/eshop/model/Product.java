package id.ac.ui.cs.advprog.eshop.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter @Setter
public class Product {
    private UUID productId;
    @NotBlank(message = "Product name can't be empty")
    private String productName;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int productQuantity;
}