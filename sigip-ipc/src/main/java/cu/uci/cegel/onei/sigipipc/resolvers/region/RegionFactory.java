package cu.uci.cegel.onei.sigipipc.resolvers.region;

import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.model.RegionInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import cu.uci.cegel.onei.sigipipc.persistence.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionFactory {

    public Region convertirRegion(RegionInput regionInput){
        return Region.builder()
                .descripcion(regionInput.getDescripcion())
                .activo(regionInput.getActivo())
                .build();
    }

    public Region updateCargo(RegionInput regionInput, Region region){
        return region.builder()
                .activo(regionInput.getActivo())
                .descripcion(regionInput.getDescripcion())
                .build();
    }
}
