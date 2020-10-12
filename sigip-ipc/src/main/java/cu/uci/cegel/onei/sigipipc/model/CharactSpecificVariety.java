package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CharactSpecificVariety {
    private Long varietyId;
    private Long characteristicId;
    private Long specificationId;
    private Boolean active;
}
