package cu.uci.cegel.onei.sigipbase.domain.usuario;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Usuario findByUsername(String username);

    List<Usuario> findByPermisosIn(Permiso permiso);

    List<Usuario> findByRoles(Rol rol);

    Usuario findByIdEquals(Long id);
}
