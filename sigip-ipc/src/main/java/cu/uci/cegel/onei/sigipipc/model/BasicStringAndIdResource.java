package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicStringAndIdResource {
    String desc;
    Long id;
    Long tipoUM;
}
