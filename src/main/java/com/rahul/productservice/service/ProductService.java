package com.rahul.productservice.service;

import com.rahul.productservice.model.Product;

import java.util.Collections;
import java.util.List;

public interface ProductService {

    public Product getProductDetails(Integer id);


    public List<Product> getAllProductsDetails();

    public Product createTheProduct(Product product);
}
