package cu.uci.cegel.onei.sigipbase.domain.rol;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.permiso.PermisoService;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RolFactory {

    private final PermisoService permisoService;

    public Rol convertir(RolDTO rolDTO) {
        HashSet<Permiso> set = new HashSet();
        if(rolDTO.getPermisos()!=null && !rolDTO.getPermisos().isEmpty()){
            set = new HashSet<>(permisoService.obtenerPermisos(rolDTO.getPermisos()));
        }
        return Rol.builder()
                .rol(rolDTO.getDescription().toUpperCase())
                .description(rolDTO.getDescription())
                .activo(rolDTO.getActivo())
                .permisos(set)
                .build();
    }

    public Rol convertir(Optional<Rol> rol, RolDTO rolDTO) {
        if (rol.isPresent()) {
            Rol rol1 = rol.get();
//            rol1.setRol(rolDTO.getRol().toUpperCase());
            rol1.setDescription(rolDTO.getDescription());
            rol1.setActivo(rolDTO.getActivo());
            rol1.setPermisos((rolDTO.getPermisos() != null && !rolDTO.getPermisos().isEmpty()) ?
                    new HashSet<>(permisoService.obtenerPermisos(rolDTO.getPermisos())) : new HashSet<>());
            return rol1;
        }
        return null;
    }
}
