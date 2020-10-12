package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecificationResource {
    Long id;
    String description;
    Boolean active;
    boolean notAllowElimination;
}
