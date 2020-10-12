package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.Currency;
import cu.uci.cegel.onei.sigipipc.persistence.DPA;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClasificadorResource {
    int id;
    int padre;
    boolean activo;
    int tipoClasificador;
    int tipoVariedad;
    String descripcion;
    String codigo;
    int id_viejo;
}
