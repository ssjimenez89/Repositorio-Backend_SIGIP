package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CatchmentInput {
    Long id;
    String users;
    float price;
    //float relative;
    //Boolean imputed;
    long incidence;
    long observation;
    //long idCurrency;
    //List<Long> dVarCaracEspec;
    Long establishment;
    Long dvarEstabId;



}
