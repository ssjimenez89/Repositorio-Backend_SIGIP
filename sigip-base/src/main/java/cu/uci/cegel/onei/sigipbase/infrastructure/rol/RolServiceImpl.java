package cu.uci.cegel.onei.sigipbase.infrastructure.rol;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.permiso.PermisoRepository;
import cu.uci.cegel.onei.sigipbase.domain.rol.*;
import cu.uci.cegel.onei.sigipbase.domain.usuario.Usuario;
import cu.uci.cegel.onei.sigipbase.domain.usuario.UsuarioRepository;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolDTO;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolFactory rolFactory;
    @Autowired
    PermisoRepository permisoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository, RolFactory rolFactory) {
        this.rolRepository = rolRepository;
        this.rolFactory = rolFactory;
    }


    @Override
    public Rol registrarRol(RolDTO rolDTO) {

        return rolRepository.save(rolFactory.convertir(rolDTO));
    }

    @Override
    public Rol actualizarRol(Long id, RolDTO rolDTO) {
        Rol rol = obtenerRol(id).get();
        List<Usuario> usuarios = usuarioRepository.findByRoles(rol);
        if (usuarios != null && !usuarios.isEmpty()) {
            usuarios.forEach(usuario -> {
                usuario.setPermisos((rolDTO.getPermisos() != null && !rolDTO.getPermisos().isEmpty()) ?
                        actualizarPermisos(usuario.getPermisos(), new HashSet<>(permisoRepository.obtenerPermisosIn(rolDTO.getPermisos()))) : new HashSet<>());
                usuarioRepository.save(usuario);
            });
        }
        return rolRepository.save(rolFactory.convertir(obtenerRol(id), rolDTO));
    }

    private Set<Permiso> actualizarPermisos(Set<Permiso> permisos, Set<Permiso> nuevos) {
        if (!nuevos.isEmpty() && nuevos != null) {
            permisos.clear();
            nuevos.forEach(permisos::add);
        }
        return permisos;
    }

    @Override
    public Page<Rol> listarRoles(RolForm rolForm, Pageable pageable) {
        return rolRepository.findAll(RolSpecs.listarRoles(rolForm), pageable);
    }

    @Override
    public Optional<Rol> obtenerRol(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol existeRol(String description) {
        return rolRepository.findFirstByDescriptionEquals(description);
    }

    @Override
    public List<Rol> obtenerRoles(boolean todos) {

        return todos ? rolRepository.findAll(Sort.by(Sort.Direction.ASC, "description")) : rolRepository.findByActivoIsTrueOrderByDescriptionAsc();
    }

    @Override
    public List<Rol> obtenerRoles(List<Long> roles) {
        return rolRepository.obtenerRolesIn(roles);
    }

    @Override
    public boolean eliminarRol(Optional<Rol> rol) {
        if (rol.isPresent()) {
            rolRepository.delete(rol.get());
            return true;
        }
        return false;
    }
}
