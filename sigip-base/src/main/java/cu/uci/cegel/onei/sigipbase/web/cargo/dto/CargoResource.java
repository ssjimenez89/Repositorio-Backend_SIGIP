package cu.uci.cegel.onei.sigipbase.web.cargo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "cargo")
public class CargoResource extends ResourceSupport {
    private Long identificador;
    private String descripcion;
    private Boolean activo;
}


