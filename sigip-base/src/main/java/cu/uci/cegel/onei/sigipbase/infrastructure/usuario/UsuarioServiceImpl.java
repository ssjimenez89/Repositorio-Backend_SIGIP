package cu.uci.cegel.onei.sigipbase.infrastructure.usuario;

import cu.uci.cegel.onei.sigipbase.domain.permiso.PermisoRepository;
import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import cu.uci.cegel.onei.sigipbase.domain.usuario.*;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioDTO;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final
    UsuarioRepository usuarioRepository;
    private final
    UsuarioFactory usuarioFactory;
    private final
    PermisoRepository permisoRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioFactory usuarioFactory, PermisoRepository permisoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioFactory = usuarioFactory;
        this.permisoRepository = permisoRepository;
    }

    @Override
    public Usuario registrarUsuario(UsuarioDTO usuarioDTO) {
        return usuarioRepository.save(usuarioFactory.convertir(usuarioDTO));
    }

    @Override
    public Usuario actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.save(usuarioFactory.convertirUpdate(obtenerUsuario(id), usuarioDTO));
    }

    @Override
    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorUsername(String username) {
        return Optional.ofNullable(usuarioRepository.findByUsername(username));
    }

    @Override
    public Page<Usuario> obtenerUsuarios(UsuarioForm usuarioForm, Pageable pageable) {

        return usuarioRepository.findAll(UsuarioSpecs.listarUsuarios(usuarioForm), pageable);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @Override
    public List<Usuario> obtenerUsuariosPorPermiso(String permiso) {
        return usuarioRepository.findByPermisosIn(permisoRepository.findByPermiso(permiso));
    }

    @Override
    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return usuarioRepository.findByRoles(rol);
    }

    @Override
    public Usuario cambiarPassword(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.save(usuarioFactory.cambiarPassword(usuarioRepository.findByIdEquals(id), usuarioDTO));
    }

    @Override
    public Usuario activarDesactivarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario user = usuarioRepository.findByIdEquals(id);
        user.setEnabled(usuarioDTO.getActivo());
        return usuarioRepository.save(user);
    }
}
