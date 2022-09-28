package knightly.testgateway.api;

import knightly.testgateway.api.dto.CreateProductRequest;
import knightly.testgateway.client.dto.ComponentDTO;
import knightly.testgateway.client.dto.ProductDTO;
import knightly.testgateway.enums.Currency;
import knightly.testgateway.services.impl.ApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    ApiServiceImpl apiService;

//    @PreAuthorize("hasRole('user')")
    @GetMapping("/components")
    public List<ComponentDTO> getComponents(@RequestParam String currency) {
        Currency currencyEnum = Currency.bronze;
        try{
            currencyEnum = getCurrencyEnumFromString(currency);
        } catch (IllegalArgumentException e) {
            logger.error("Illegal State Exception turning Param into Enum on get ComponentRequest");
        }
        return this.apiService.getAllComponentDTOs(currencyEnum);

    }

//    @PreAuthorize("hasRole('user')")
    @GetMapping("/products")
    public List<ProductDTO> getProducts(@RequestParam String currency) {
        Currency currencyEnum = Currency.bronze;
        try{
            currencyEnum = getCurrencyEnumFromString(currency);
        } catch (IllegalArgumentException e) {
            logger.error("Illegal State Exception turning Param into Enum on getProducts");
        }
        return this.apiService.getAllProductDTOs(currencyEnum);
    }

    @PostMapping("/createProduct")
    public void createProduct(@RequestBody CreateProductRequest createProductRequest)  {
        this.apiService.createProduct(createProductRequest);
    }

    @GetMapping("/clown")
    public String test(){
        return "clown";
    }

    private Currency getCurrencyEnumFromString(String string) throws IllegalStateException {
        return Currency.valueOf(string);
    }
//
//    @GetMapping("/test")
//    public String test2(){
//        return "test";
//    }
//
//    private List<ComponentDTO> createTestComponents() {
//        return List.of(
//                new ComponentDTO(
//                        1, "floomp" ,new BigDecimal("2.20"), "description1", 2, 3, "ead", 2, 4,5)
//                ,new ComponentDTO(
//                        2, "fleemp" ,new BigDecimal("5.30"), "description2", 2, 3, "foot", 2, 4,5)
//                ,new ComponentDTO(
//                        1, "flemp" ,new BigDecimal("2.20"), "description", 2, 3, "ead", 2, 4,5)
//        );
//    }
//
//    private List<ProductDTO> createTestProducts () {
//        return List.of(
//                new ProductDTO(0, "Product1", new BigDecimal("400"), createTestComponents())
//                ,new ProductDTO(1, "Product2", new BigDecimal("700"), createTestComponents())
//        );
//    }
}
