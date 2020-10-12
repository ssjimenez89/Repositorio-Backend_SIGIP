package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaracteristicaEspecificacionResource {
    BasicStringAndIdResource carac;
    BasicStringAndIdResource spec;
}
