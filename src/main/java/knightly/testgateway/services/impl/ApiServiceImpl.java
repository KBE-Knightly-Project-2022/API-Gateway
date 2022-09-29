package knightly.testgateway.services.impl;

import knightly.testgateway.api.dto.CreateProductRequest;
import knightly.testgateway.client.ProductService;
import knightly.testgateway.client.dto.AgeReply;
import knightly.testgateway.client.dto.ComponentDTO;
import knightly.testgateway.client.dto.ProductDTO;
import knightly.testgateway.enums.Currency;
import knightly.testgateway.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    ProductService productService;


    @Override
    public List<ComponentDTO> getAllComponentDTOs(Currency currency) {
        return this.productService.getComponentDTOs(currency);
    }

    @Override
    public List<ProductDTO> getAllProductDTOs(Currency currency) {
        return this.productService.getProductDTOs(currency);
    }

    @Override
    public void createProduct(CreateProductRequest createProductRequest) {
       this.productService.createProductFromIDs(createProductRequest.getComponentIDs() , createProductRequest.getName());
    }

    @Override
    public AgeReply getAge(String name) {
        return this.productService.getAge(name);
    }
}
