package cu.uci.cegel.onei.sigipbase.web.permiso.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "permiso")
public class PermisoResource extends ResourceSupport {

    private Long idpermiso;
    private String permiso;
    private String description;
    private Boolean activo;
}
