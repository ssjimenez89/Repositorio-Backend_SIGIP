package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.DPA;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class IndiceArticuloResource {
    Long id;
    Long idvariedad;
    Float indice;
    LocalDate fecha;
    String mes;
    String nombreVariedad;
    List<CaracteristicaEspecificacionResource> varCaracEsp;
    DPA dpa;
}
