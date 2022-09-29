package knightly.testgateway.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import knightly.testgateway.client.dto.AgeReply;
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

import java.util.Collections;
import java.util.List;

public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${routing.key.product.service}")
    private String routingKeyProductService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DirectExchange directExchange;

    public List<ComponentDTO> getAllComponentDTOs(Currency currency) {
        ProductRequest productRequest = new ProductRequest(RequestType.getComponents, currency);
        String productRequestJson = convertProductRequestToJson(productRequest);
        String productReply = "";
        try {
            productReply = rabbitTemplate.convertSendAndReceive(
                    directExchange.getName()
                    , routingKeyProductService
                    , productRequestJson).toString();
        } catch (AmqpException e) {
            logger.error("Error Connecting to Productservice via RabbitMQ");
            return Collections.emptyList();
        }

        if (checkReplyForError(productReply)){
            throw new RuntimeException("Error in Product Service handling the Request");
        }
        return convertJsonToComponentDTOList(productReply);
    }

    public List<ProductDTO> getAllProductDTOs(Currency currency) {
        ProductRequest productRequest = new ProductRequest(RequestType.getProducts, currency);
        String productRequestJson = convertProductRequestToJson(productRequest);
        String productReply = "";
        try {
            productReply = rabbitTemplate.convertSendAndReceive(
                    directExchange.getName()
                    , routingKeyProductService
                    , productRequestJson).toString();
        } catch (AmqpException e) {
            logger.error("Error Connecting to Productservice via RabbitMQ");
            return Collections.emptyList();
        }
        if (checkReplyForError(productReply)){
            throw new RuntimeException("Error in Product Service handling the Request");
        }
        return convertJsonToProductDTOList(productReply);
    }

    public void createProductFromIDs(List<Long> componentIds, String name) {
        ProductRequest productRequest = new ProductRequest(RequestType.createProduct, componentIds, name);
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

        if(checkReplyForError(productReply)) {
            logger.error("Error while Creating Product in Product Service");
            logger.error(productReply);
        } else {
            logger.info("Successfully created Product");
        }
    }

    public AgeReply getAge(String name) {
        ProductRequest productRequest = new ProductRequest(RequestType.getAge, name);
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

        return convertJsonDTOtoAgeReply(productReply);
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

    private AgeReply convertJsonDTOtoAgeReply(String jsonDTO) {
        return new Gson().fromJson(jsonDTO, AgeReply.class);
    }

    private boolean checkReplyForError(String reply) {
        return (reply.contains("[Error]"));
    }

}
