package cu.uci.cegel.onei.sigipbase.domain.rol;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolDTO;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RolService {

    Rol registrarRol(RolDTO rolDTO);

    Rol actualizarRol(Long id, RolDTO rolDTO);

    Page<Rol> listarRoles(RolForm rolForm, Pageable pageable);

    Optional<Rol> obtenerRol(Long id);

    Rol existeRol(String description);

    List<Rol> obtenerRoles(boolean todos);

    List<Rol> obtenerRoles(List<Long> roles);

    boolean eliminarRol(Optional<Rol> rol);
}
