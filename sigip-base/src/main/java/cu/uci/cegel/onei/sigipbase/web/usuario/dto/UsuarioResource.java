package cu.uci.cegel.onei.sigipbase.web.usuario.dto;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "usuario")
public class UsuarioResource extends ResourceSupport {
    private Long idTrabajador;
    private String primernombre;
    private String segundonombre;
    private String primerapellido;
    private String segundoapellido;
    private Boolean activo;
    private String fechainicio;
    private String fechafin;
    private String nombrecompleto;
    private String carnetidentidad;
    private String email;

    private String username;
    private String password;
    private Long dpa;
    private Long cargo;
    private Set<Permiso> permisos;
    private Set<Rol> roles;

    private String cod_dpa;
    private String denom_cargo;

}


