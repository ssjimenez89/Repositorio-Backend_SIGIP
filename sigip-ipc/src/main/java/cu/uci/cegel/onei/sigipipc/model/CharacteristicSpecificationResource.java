package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CharacteristicSpecificationResource {
    Long id;
    String description;
    boolean active;
    List<SpecificationResource> especifications;
    int totalespecifications;
    boolean notAllowElimination;
}
