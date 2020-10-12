package cu.uci.cegel.onei.sigipipc.model;

import lombok.Data;

import java.util.List;

@Data
public class ClassifierInput {
    Long id;
    String description;
    String action;
    Long parent;
    Long oneiIndex;
    String code;
    List<ClassifierWeighingInput> weights;
    Long idclassifierType;
    Long varietyType;
    List<varietyCharactSpecificsInput> varietyCharactSpecifics;
    List<Long> specifics;
    List<Long> marketCurrencies;
}
