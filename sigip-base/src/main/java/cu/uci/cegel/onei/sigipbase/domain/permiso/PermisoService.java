package cu.uci.cegel.onei.sigipbase.domain.permiso;

import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoDTO;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PermisoService {

    Permiso registrarPermiso(PermisoDTO permisoDTO);

    Permiso actualizarPermiso(Long idpermiso, PermisoDTO permisoDTO);

    Optional<Permiso> obtenerPermiso(Long idpermiso);

    Boolean desactivarPermiso(Long idpermiso);

    List<Permiso> listarPermisos();

    List<Permiso> buscarPermisosQueContengan(List<Long> Permiso);

    Page<Permiso> listarPermisosPage(PermisoForm permisoForm, Pageable pageable);

    List<Permiso> obtenerPermisos(List<Long> permisos);
    //List<Permiso> obtenerPermisosByRoles(Set<Rol> roles);
}
