package cu.uci.cegel.onei.sigipbase.domain.cargo;

import cu.uci.cegel.onei.sigipbase.web.cargo.dto.CargoDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CargoFactory {

    public Cargo convertir(CargoDTO cargoDTO) {
        return Cargo.builder()
                .id(cargoDTO.getId())
                .descripcion(cargoDTO.getDescripcion())
                .activo(cargoDTO.getActivo())
                .build();
    }

    public Cargo convertir(Optional<Cargo> cargo, CargoDTO cargoDTO) {
        if (cargoDTO!=null) {
            Cargo cargo1 = cargo.get();
            cargo1.setId(cargoDTO.getId());
            cargo1.setActivo(cargoDTO.getActivo());
            cargo1.setDescripcion(cargoDTO.getDescripcion());
            return cargo1;
        }
        return null;
    }
}
