package knightly.testgateway.api;

import knightly.testgateway.api.dto.CreateProductRequest;
import knightly.testgateway.client.dto.AgeReply;
import knightly.testgateway.client.dto.ComponentDTO;
import knightly.testgateway.client.dto.ProductDTO;
import knightly.testgateway.enums.Currency;
import knightly.testgateway.services.impl.ApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasRole('user')")
    @PostMapping("/createProduct")
    public void createProduct(@RequestBody CreateProductRequest createProductRequest)  {
        this.apiService.createProduct(createProductRequest);
    }

    @GetMapping("/age")
    public AgeReply getAge(@RequestParam String name){
        return this.apiService.getAge(name);
    }

    @GetMapping("/clown")
    public String test(){
        return "clown";
    }

    private Currency getCurrencyEnumFromString(String string) throws IllegalStateException {
        return Currency.valueOf(string);
    }
}
