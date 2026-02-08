package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter @Setter
public class Product {
    private UUID productid;
    private String productName;
    private int productQuantity;
}