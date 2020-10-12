package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CargoService {

    //QUERYS!!!!!!

    List<Cargo> listarCargos();

    List<Cargo> listarCargosPaging(int page, int size);

    Cargo obtenerCargo(Long id);

    Long totalCargos();

    List<Cargo> cargosPorDescripcion(String description, int page, int size);

    Integer totalCargosPorDescripcion(String descripcion);

    //MUTATIONS!!!!!!

    Cargo registrarCargo(CargoInput cargoInput);

    Cargo actualizarCargo(CargoInput cargoInput);
}
