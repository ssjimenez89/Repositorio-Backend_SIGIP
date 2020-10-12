package cu.uci.cegel.onei.sigipipc.resolvers.cargo;

import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import org.springframework.stereotype.Component;

@Component
public class CargoFactory {

    public Cargo convertirCargo(CargoInput cargoInput){
        return Cargo.builder()
                .descripcion(cargoInput.getDescripcion())
                .activo(cargoInput.getActivo())
                .build();
    }

    public Cargo updateCargo(CargoInput cargoInput, Cargo cargo){
        return cargo.builder()
                .activo(cargoInput.getActivo())
                .descripcion(cargoInput.getDescripcion())
                .build();
    }
}
