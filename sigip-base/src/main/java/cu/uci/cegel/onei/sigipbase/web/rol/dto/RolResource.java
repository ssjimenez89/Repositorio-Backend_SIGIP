package cu.uci.cegel.onei.sigipbase.web.rol.dto;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "permiso")
public class RolResource extends ResourceSupport {
    private Long idRol;
    private String rol;
    private String description;
    private Boolean activo;
    private Set<Permiso> permisos;
}
