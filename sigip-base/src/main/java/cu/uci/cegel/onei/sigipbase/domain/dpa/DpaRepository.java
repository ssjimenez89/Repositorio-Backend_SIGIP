package cu.uci.cegel.onei.sigipbase.domain.dpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DpaRepository extends JpaRepository<Dpa, Long>, JpaSpecificationExecutor<Dpa> {

//    @Query("SELECT p FROM Dpa p WHERE p.id IN :permisos")
//    List<Dpa> obtenerPermisosIn(@Param("permisos") List<Long> permisos);
    Dpa findByDescripcion(String dpa);

    Dpa findByIdEquals(Long id);

    List<Dpa> findByActivoIsTrueOrderByDescripcionAsc();
}
