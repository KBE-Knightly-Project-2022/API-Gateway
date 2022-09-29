package knightly.testgateway.services;


import knightly.testgateway.api.dto.CreateProductRequest;
import knightly.testgateway.client.ProductService;
import knightly.testgateway.client.dto.ComponentDTO;
import knightly.testgateway.enums.Currency;
import knightly.testgateway.services.impl.ApiServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @InjectMocks
    ApiServiceImpl apiService;
    @Mock
    ProductService productService;

    @Test
    public void getAllComponentDTO() {
        Currency currency = Currency.cow;

        apiService.getAllComponentDTOs(currency);

        Mockito.verify(productService, Mockito.times(1)).getComponentDTOs(currency);
    }

    @Test
    public void getAllProductDTOs() {
        Currency currency = Currency.donkey;

        apiService.getAllProductDTOs(currency);

        Mockito.verify(productService, Mockito.times(1)).getProductDTOs(currency);
    }

    @Test
    public void getProductRequest() {
        CreateProductRequest createProductRequest = getCreateProductRequest();

        apiService.createProduct(createProductRequest);

        Mockito.verify(productService, Mockito.times(1)).createProductFromIDs(List.of(1L,3L), "Svenjamin");
    }

    @Test
    public void getAge() {
        String name = "Beneficial Cucumber";

        apiService.getAge(name);

        Mockito.verify(productService, Mockito.times(1)).getAge(name);
    }

    private CreateProductRequest getCreateProductRequest() {
        return new CreateProductRequest("Svenjamin",
                List.of(new ComponentDTO(1L, "josh", new BigDecimal("100"), "yes", 1,2,"foot", 22, 4, 2)
                ,new ComponentDTO(3L, "matt", new BigDecimal("200"), "no", 1,2,"foot", 22, 4, 2)));
    }
}
