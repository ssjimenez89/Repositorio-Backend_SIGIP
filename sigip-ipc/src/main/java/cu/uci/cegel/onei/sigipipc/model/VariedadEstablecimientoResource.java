package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyEstablishment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VariedadEstablecimientoResource {
    List<Long> listIdVarEstab;
    VarietyEstablishment varietyEstablishment;
    boolean active;
    String establecimientoDescripcion;
    String stateDescripcion;
    String mercadoDescripcion;
    String monedaDescripcion;
    String dpaDescripcion;
    String variedadDescripcion;
    String unidadMedidaDescripcion;
    String cantidadDescripcion;
    Long dayPlanning1Id1;
    String dayPlanning1;
    Long dayPlanning1Id2;
    String dayPlanning2;
    Long dayPlanning1Id3;
    String dayPlanning3;
    Long dayPlanning1Id4;
    String dayPlanning4;
}
