package knightly.testgateway.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import knightly.testgateway.client.dto.ComponentDTO;
import knightly.testgateway.client.dto.ProductDTO;
import knightly.testgateway.client.dto.ProductRequest;
import knightly.testgateway.enums.Currency;
import knightly.testgateway.enums.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class ProductClient {

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

    @Value("${routing.key.product.service}")
    private String routingKeyProductService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DirectExchange directExchange;

    public List<ComponentDTO> makeComponentRequest(Currency currency) {
        ProductRequest productRequest = new ProductRequest(RequestType.getComponents, Currency.gold);
        String productRequestJson = convertProductRequestToJson(productRequest);
        String productReply = "";
        try {
            productReply = rabbitTemplate.convertSendAndReceive(
                    directExchange.getName()
                    , routingKeyProductService
                    , productRequestJson).toString();
        } catch (AmqpException e) {
            logger.error("Error Connecting to Productservice via RabbitMQ");
        }

        return convertJsonToComponentDTOList(productReply);
    }

    public List<ProductDTO> makeProductRequest(Currency currency) {
        ProductRequest productRequest = new ProductRequest(RequestType.getProducts, Currency.gold);
        String productRequestJson = convertProductRequestToJson(productRequest);
        String productReply = "";
        try {
            productReply = rabbitTemplate.convertSendAndReceive(
                    directExchange.getName()
                    , routingKeyProductService
                    , productRequestJson).toString();
        } catch (AmqpException e) {
            logger.error("Error Connecting to Productservice via RabbitMQ");
        }

        return convertJsonToProductDTOList(productReply);
    }

    private String convertProductRequestToJson(ProductRequest productRequest) {
        return new Gson().toJson(productRequest, ProductRequest.class);
    }

    private List<ComponentDTO> convertJsonToComponentDTOList(String componentDTOJson) {
        return new Gson().fromJson(
                componentDTOJson,
                new TypeToken<List<ComponentDTO>>() {
                }.getType());
    }

    private List<ProductDTO> convertJsonToProductDTOList(String productDTOJson) {
        return new Gson().fromJson(
                productDTOJson,
                new TypeToken<List<ProductDTO>>() {
                }.getType());
    }
}
