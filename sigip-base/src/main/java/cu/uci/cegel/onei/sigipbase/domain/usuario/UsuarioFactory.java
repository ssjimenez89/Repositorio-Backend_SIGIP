package cu.uci.cegel.onei.sigipbase.domain.usuario;

import cu.uci.cegel.onei.sigipbase.domain.cargo.Cargo;
import cu.uci.cegel.onei.sigipbase.domain.cargo.CargoService;
import cu.uci.cegel.onei.sigipbase.domain.dpa.Dpa;
import cu.uci.cegel.onei.sigipbase.domain.dpa.DpaService;
import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.permiso.PermisoService;
import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import cu.uci.cegel.onei.sigipbase.domain.rol.RolService;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class UsuarioFactory {

    private final RolService roleService;
    private final PermisoService permisoService;
    private final DpaService dpaService;
    private final CargoService cargoService;

    public Usuario convertir(UsuarioDTO usuarioDTO) {
        Set<Rol> roles = (usuarioDTO.getRoles() != null && !usuarioDTO.getRoles().isEmpty()) ?
                new HashSet<>(roleService.obtenerRoles(usuarioDTO.getRoles())) : null;
        Set<Permiso> permisos_aislados = (usuarioDTO.getPermisos() != null && !usuarioDTO.getPermisos().isEmpty()) ?
                new HashSet<>(permisoService.obtenerPermisos(usuarioDTO.getPermisos())) : null;
        Dpa objdpa = (usuarioDTO.getDpa()!=null)?dpaService.obtenerDpa(usuarioDTO.getDpa()):null;
        Cargo objCargo = (usuarioDTO.getCargo()!=null)?cargoService.obtenerCargo(usuarioDTO.getCargo()):null;

        //Estas fechas no se estan recogiendo
        //formatearFechaDateTime(usuarioDTO);
        return Usuario.builder()
                .primernombre(usuarioDTO.getPrimernombre())
                .segundonombre(usuarioDTO.getSegundonombre())
                .primerapellido(usuarioDTO.getPrimerapellido())
                .segundoapellido(usuarioDTO.getSegundoapellido())
                .username(usuarioDTO.getUsername())
                .password(usuarioDTO.getPassword() != "" ? new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()) : "")
                .permisos((roles != null && !roles.isEmpty()) ?
                        generarPermisosByRoles(roles, permisos_aislados) : null)
                .roles(roles)
                .enabled(usuarioDTO.getActivo())
                .dpa(objdpa)
                .cargo(objCargo)
                .correo(usuarioDTO.getEmail())
                .locked(true)
                .credentialsexpired(true)
                .expired(true)
                .build();
    }

    public Set<Permiso> generarPermisosByRoles(Set<Rol> roles, Set<Permiso> permisos_aislados) {
        Set<Permiso> permisos = new HashSet<>();
        roles.stream().forEach((e) -> permisos.addAll(e.getPermisos()));
        if (permisos_aislados != null) {
            permisos_aislados.removeAll(permisos);
            permisos.addAll(permisos_aislados);
        }
        return permisos;

    }
    public Set<Permiso> generarPermisosByRolesActualizando(Set<Rol> roles, Set<Permiso> permisos_aislados) {
        Set<Permiso> permisos = new HashSet<>();
        roles.stream().forEach((e) -> permisos.addAll(e.getPermisos()));
        if (permisos_aislados != null) {
            permisos_aislados.removeAll(permisos);
            permisos.addAll(permisos_aislados);
        }
        return permisos;

    }

    public Usuario convertir(Optional<Usuario> usuarioOptional, UsuarioDTO usuarioDTO) {
        if (usuarioOptional.isPresent()) {
            Set<Rol> roles = (usuarioDTO.getRoles() != null && !usuarioDTO.getRoles().isEmpty()) ?
                    new HashSet<>(roleService.obtenerRoles(usuarioDTO.getRoles())) : null;
            Set<Permiso> permisos_aislados = (usuarioDTO.getPermisos() != null && !usuarioDTO.getPermisos().isEmpty()) ?
                    new HashSet<>(permisoService.obtenerPermisos(usuarioDTO.getPermisos())) : null;
            Usuario usuario = usuarioOptional.get();
            usuario.setPrimernombre(usuarioDTO.getPrimernombre());
            usuario.setSegundonombre(usuarioDTO.getSegundonombre());
            usuario.setPrimerapellido(usuarioDTO.getPrimerapellido());
            usuario.setSegundoapellido(usuarioDTO.getSegundoapellido());
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(usuarioDTO.getPassword() != "" ? new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()) : usuarioOptional.get().getPassword());
            usuario.setPermisos((roles != null && !roles.isEmpty()) ?
                    generarPermisosByRoles(roles, permisos_aislados) : null);
            usuario.setRoles(roles);
            usuario.setEnabled(usuarioDTO.getActivo());
            formatearFechaDateTime(usuarioDTO);
            usuario.setFechainicio(usuarioDTO.getFechainicio() != "" ? usuarioDTO.obtenerFechaInicio() : null);
            usuario.setFechafin(usuarioDTO.getFechafin() != "" ? usuarioDTO.obtenerFechaFin() : null);
            usuario.setCarnetidentidad(usuarioDTO.getCarnetidentidad());
            usuario.setCorreo(usuarioDTO.getEmail());
            return usuario;
        }
        return null;
    }

    public Usuario convertirUpdate(Optional<Usuario> usuarioOptional, UsuarioDTO usuarioDTO) {
        if (usuarioOptional.isPresent()) {
            Set<Rol> roles = (usuarioDTO.getRoles() != null && !usuarioDTO.getRoles().isEmpty()) ?
                    new HashSet<>(roleService.obtenerRoles(usuarioDTO.getRoles())) : null;
            Set<Permiso> permisos_aislados = (usuarioDTO.getPermisos() != null && !usuarioDTO.getPermisos().isEmpty()) ?
                    new HashSet<>(permisoService.obtenerPermisos(usuarioDTO.getPermisos())) : null;

            Dpa objdpa = (usuarioDTO.getDpa()!=null)?dpaService.obtenerDpa(usuarioDTO.getDpa()):null;
            Cargo objCargo = (usuarioDTO.getCargo()!=null)?cargoService.obtenerCargo(usuarioDTO.getCargo()):null;

            Usuario usuario = usuarioOptional.get();
            usuario.setPrimernombre(usuarioDTO.getPrimernombre());
            usuario.setSegundonombre(usuarioDTO.getSegundonombre());
            usuario.setPrimerapellido(usuarioDTO.getPrimerapellido());
            usuario.setSegundoapellido(usuarioDTO.getSegundoapellido());
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPermisos((roles != null && !roles.isEmpty()) ?
                    generarPermisosByRolesActualizando(roles, permisos_aislados) : null);
            usuario.setRoles(roles);
            usuario.setEnabled(usuarioDTO.getActivo());
            usuario.setCorreo(usuarioDTO.getEmail());
            usuario.setCargo(objCargo);
            usuario.setDpa(objdpa);
            return usuario;
        }
        return null;
    }

    private UsuarioDTO formatearFechaDateTime(UsuarioDTO usuarioDTO) {
        usuarioDTO.setFechainicio(usuarioDTO.getFechainicio() != "" ? LocalDateTime.of(LocalDate.parse(usuarioDTO.getFechainicio()), LocalTime.now()).toString() : null);
        usuarioDTO.setFechafin(usuarioDTO.getFechafin() != "" ? LocalDateTime.of(LocalDate.parse(usuarioDTO.getFechafin()), LocalTime.now()).toString() : null);
        return usuarioDTO;
    }

    public Usuario cambiarPassword(Usuario usuario, UsuarioDTO usuarioDTO){
        usuario.setPassword(usuarioDTO.getPassword() != "" ? new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()) : usuario.getPassword());
        return usuario;
    }
}
