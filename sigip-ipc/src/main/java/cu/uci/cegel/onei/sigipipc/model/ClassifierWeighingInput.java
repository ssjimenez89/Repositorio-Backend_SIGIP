package cu.uci.cegel.onei.sigipipc.model;

import lombok.Data;

@Data
public class ClassifierWeighingInput {
    Long id;
    Long weighingType;
    Long currency;
    Double value;
}
