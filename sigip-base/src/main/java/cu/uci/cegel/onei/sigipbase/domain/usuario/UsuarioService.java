package cu.uci.cegel.onei.sigipbase.domain.usuario;

import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioDTO;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario registrarUsuario(UsuarioDTO usuarioDTO);

    Usuario actualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    Optional<Usuario> obtenerUsuario(Long id);

    Optional<Usuario> obtenerUsuarioPorUsername(String username);

    Page<Usuario> obtenerUsuarios(UsuarioForm usuarioForm, Pageable pageable);

    List<Usuario> obtenerUsuarios();

    List<Usuario> obtenerUsuariosPorPermiso(String permiso);

    List<Usuario> obtenerUsuariosPorRol(Rol rol);

    Usuario cambiarPassword(Long id, UsuarioDTO usuarioDTO);

    Usuario activarDesactivarUsuario(Long id, UsuarioDTO usuarioDTO);
}
