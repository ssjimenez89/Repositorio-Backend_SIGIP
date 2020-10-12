package cu.uci.cegel.onei.sigipbase.infrastructure.cargo;

import cu.uci.cegel.onei.sigipbase.domain.cargo.Cargo;
import cu.uci.cegel.onei.sigipbase.domain.cargo.CargoRepository;
import cu.uci.cegel.onei.sigipbase.domain.cargo.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    CargoRepository cargoRepository;

    @Override
    public List<Cargo> listarTodosCargos() {
        return cargoRepository.findByActivoIsTrueOrderByDescripcionAsc();
    }

    @Override
    public Cargo obtenerCargo(Long id) {
        return cargoRepository.findByIdEquals(id);
    }

    @Override
    public Optional<Cargo> obtenerCargoNew(Long id) {
        return cargoRepository.findById(id);
    }
}
