package com.rahul.productservice.service;

import com.rahul.productservice.model.Category;
import com.rahul.productservice.model.Product;
import com.rahul.productservice.repository.CategoryRepo;
import com.rahul.productservice.repository.ProductRepo;
import com.rahul.productservice.repository.projection.ProductProjection;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;
    public SelfProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }


    @Override
    public Product getProductDetails(Integer id){
        Product response =  productRepo.findById(id).get();
        List<Category>categories = categoryRepo.findAll();
        System.out.println("Fetch list of  Categories");

        return response;
    }



    @Override
    public List<Product> getAllProductsDetails(){
        ProductProjection response = productRepo.getProductNameByTitle("rahul");
        System.out.println("product response :>>"+response.getDescription()+" "+ response.getTitle());
        return productRepo.findAll();
    }

    @Override
    public Product createTheProduct(Product product){

        Product prod = new Product();
        Category category = new Category();

        prod.setTitle(product.getTitle());
        prod.setDescription(product.getDescription());
        prod.setImageURL(product.getImageURL());
        prod.setCreatedAt(new Date());
        prod.setUpdatedAt(new Date());

        // before setting the category we have to check that particular category is there in db or not
        Category exisCategory = categoryRepo.findByTitle(product.getTitle());
        if(exisCategory == null){
            category.setTitle(product.getTitle());
        }
        prod.setCategory(category);

        Product response = productRepo.save(prod);

        return response;
    }
}
