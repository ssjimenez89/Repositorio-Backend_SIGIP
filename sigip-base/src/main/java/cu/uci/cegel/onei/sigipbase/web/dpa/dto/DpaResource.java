package cu.uci.cegel.onei.sigipbase.web.dpa.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "dpa")
public class DpaResource extends ResourceSupport {
    private Long identificador;
    private String descripcion;
    private String codigodpa;
    private Boolean activo;
    private Long idpadre;
    private String codigo_descripcion;
}


