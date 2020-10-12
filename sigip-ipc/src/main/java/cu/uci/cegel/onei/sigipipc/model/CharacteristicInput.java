package cu.uci.cegel.onei.sigipipc.model;

import lombok.Data;

import java.util.List;

@Data
public class CharacteristicInput {
    String description;
    boolean active;
    List<SpecificationInput> especifications;
}
