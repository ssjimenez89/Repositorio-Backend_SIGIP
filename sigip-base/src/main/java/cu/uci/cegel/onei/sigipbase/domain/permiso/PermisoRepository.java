package cu.uci.cegel.onei.sigipbase.domain.permiso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long>, JpaSpecificationExecutor<Permiso> {

    @Query("SELECT p FROM Permiso p WHERE p.id IN :permisos")
    List<Permiso> obtenerPermisosIn(@Param("permisos") List<Long> permisos);

    Permiso findByPermiso(String permiso);

    List<Permiso> findByActivoIsTrueOrderByDescription();

}
