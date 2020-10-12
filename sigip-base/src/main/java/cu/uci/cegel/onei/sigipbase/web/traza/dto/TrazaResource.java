package cu.uci.cegel.onei.sigipbase.web.traza.dto;


import lombok.Data;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Data
@Setter
public class TrazaResource extends ResourceSupport {

    String idTraza;
    String descripcion;
    String fecha;
    String usuario;
    String ip;
    String tipoDeTraza;
    String tipoOperacion;
    String codEntidad;
    String dirEntidad;
    String desEntidad;
}
