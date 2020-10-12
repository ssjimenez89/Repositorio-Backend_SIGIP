package cu.uci.cegel.onei.sigipbase.domain.rol;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long>, JpaSpecificationExecutor<Rol> {

    @Query("SELECT r FROM Rol r WHERE r.id IN :roles")
    List<Rol> obtenerRolesIn(@Param("roles") List<Long> roles);

    Rol findByRol(String rol);

    Rol findByDescription (String rol);

    Rol findFirstByDescriptionEquals (String description);

    List<Rol> findByActivoIsTrueOrderByDescriptionAsc();
}
