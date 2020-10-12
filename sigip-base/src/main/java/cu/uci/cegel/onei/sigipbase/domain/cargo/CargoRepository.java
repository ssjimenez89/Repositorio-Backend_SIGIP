package cu.uci.cegel.onei.sigipbase.domain.cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long>, JpaSpecificationExecutor<Cargo> {

    Cargo findByDescripcion(String cargo);

    Cargo findByIdEquals(Long id);

    List<Cargo> findByActivoIsTrueOrderByDescripcionAsc();
}
