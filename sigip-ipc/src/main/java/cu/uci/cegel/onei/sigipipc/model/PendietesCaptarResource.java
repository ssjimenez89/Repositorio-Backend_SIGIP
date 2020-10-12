package cu.uci.cegel.onei.sigipipc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PendietesCaptarResource {
    String variedadDescription;
    Long variedadId;
    List<CaracteristicaEspecificacionResource> specs;
    String establecimiento;
    String mercado;
    Long total;
    String dia;
    Long varEstabId;
    String moneda;
    List<Long> varCarEsp;
    Float precioMesAnterior;
}
