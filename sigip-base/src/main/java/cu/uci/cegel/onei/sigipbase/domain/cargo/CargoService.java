package cu.uci.cegel.onei.sigipbase.domain.cargo;


import java.util.List;
import java.util.Optional;

public interface CargoService {

    List<Cargo> listarTodosCargos();

    Cargo obtenerCargo(Long id);

    Optional<Cargo> obtenerCargoNew(Long id);
}
