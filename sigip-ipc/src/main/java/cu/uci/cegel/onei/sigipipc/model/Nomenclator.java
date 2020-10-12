package cu.uci.cegel.onei.sigipipc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Nomenclator {
    private Integer identificador;
    private String description;
    private String type;
    private Boolean active;
}
