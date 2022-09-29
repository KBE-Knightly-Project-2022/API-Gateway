package knightly.testgateway.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgeReply {

    public int age;
    public long count;
    public String name;
}
