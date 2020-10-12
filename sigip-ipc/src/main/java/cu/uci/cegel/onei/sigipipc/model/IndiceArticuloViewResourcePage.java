package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IndiceArticuloViewResourcePage {
    Long totalItems;
    List<IndiceArticuloResource> resources;
}
