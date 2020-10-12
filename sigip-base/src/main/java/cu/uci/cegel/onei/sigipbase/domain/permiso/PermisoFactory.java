package cu.uci.cegel.onei.sigipbase.domain.permiso;

import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PermisoFactory {

    public Permiso convertir(PermisoDTO permisoDTO) {
        return Permiso.builder()
                .permiso(permisoDTO.getPermiso())
                .description(permisoDTO.getDescription())
                .activo(permisoDTO.getActivo())
                .build();
    }

    public Permiso convertir(Optional<Permiso> permiso, PermisoDTO permisoDTO) {
        if (permiso.isPresent()) {
            Permiso permiso1 = permiso.get();
            permiso1.setPermiso(permisoDTO.getPermiso());
            permiso1.setDescription(permisoDTO.getDescription());
            permiso1.setActivo(permisoDTO.getActivo());
            return permiso1;
        }
        return null;
    }
}
