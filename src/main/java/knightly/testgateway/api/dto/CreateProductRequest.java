package knightly.testgateway.api.dto;

import knightly.testgateway.client.dto.ComponentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class CreateProductRequest {
    String name;
    List<ComponentDTO> product_components;

    public List<Long> getComponentIDs() {
        return product_components.
                stream()
                .map(ComponentDTO::getId)
                .collect(Collectors.toList());
    }
}
