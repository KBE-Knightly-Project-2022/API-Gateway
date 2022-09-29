package knightly.testgateway.services;

import knightly.testgateway.api.dto.CreateProductRequest;
import knightly.testgateway.client.dto.AgeReply;
import knightly.testgateway.client.dto.ComponentDTO;
import knightly.testgateway.client.dto.ProductDTO;
import knightly.testgateway.enums.Currency;

import java.util.List;

public interface ApiService {

    List<ComponentDTO> getAllComponentDTOs(Currency currency);
    List<ProductDTO> getAllProductDTOs(Currency currency);
    void createProduct(CreateProductRequest createProductRequest);
    AgeReply getAge(String name);
}
