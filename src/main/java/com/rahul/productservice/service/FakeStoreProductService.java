package com.rahul.productservice.service;

import com.rahul.productservice.dto.FakeStoreResponseDTO;
import com.rahul.productservice.model.Category;
import com.rahul.productservice.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreResponse")
public class FakeStoreProductService {

    private RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProductDetails(Integer id){

        Product product = new Product();

        // call fakestore API -->  call another service called RestTemplate
        ResponseEntity<FakeStoreResponseDTO> fakeStoreResponse = restTemplate.getForEntity("https://fakestoreapi.com/products/"+id, FakeStoreResponseDTO.class);

        // get the response
        FakeStoreResponseDTO response = fakeStoreResponse.getBody();
        if(response == null){
            throw new IllegalArgumentException("FakeStoreResponse is null");
        }

        // map the response to our product  model
        product = convertFakeStoreResponseToProduct(response);
        return product;
    }

    private Product convertFakeStoreResponseToProduct(FakeStoreResponseDTO response) {

        Product product = new Product();
        Category category = new Category();
        category.setTitle(response.getCategory());
        product.setCategory(category);

        product.setId(response.getId());
        product.setDescription(response.getDescription());
        product.setImageURL(response.getImage());
        product.setTitle(response.getTitle());

        return product;
    }





    public List<Product> getAllProductsDetails() {
        List<Product> response = new ArrayList<>();
        ResponseEntity<FakeStoreResponseDTO[]> fakeStoreResponse = restTemplate.getForEntity("https://fakestoreapi.com/products/", FakeStoreResponseDTO[].class);

        for(FakeStoreResponseDTO fakeStoreResponseDTO : fakeStoreResponse.getBody()){
            response.add(convertFakeStoreResponseToProduct(fakeStoreResponseDTO));
        }
        return response;
    }





    public Product createTheProduct(Product product) {
        Product response = new Product();

        FakeStoreResponseDTO requestBody = new FakeStoreResponseDTO();
        requestBody.setCategory(product.getCategory().getTitle());
        requestBody.setDescription(product.getDescription());
        requestBody.setTitle(product.getTitle());
        requestBody.setImage(product.getImageURL());

        ResponseEntity<FakeStoreResponseDTO> fakeStoreResponse  =
                restTemplate.postForEntity("https://fakestoreapi.com/products", requestBody, FakeStoreResponseDTO.class);

         response = convertFakeStoreResponseToProduct(fakeStoreResponse.getBody());
        return response;
    }
}






// call fakestore API -->  call another service called RestTemplate
// get the response
// map the response to our product  model
// return