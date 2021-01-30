package rain.people.Dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Introspected
public class Counters {

    private String id;
    private long seq;
}



