package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.model.RegionInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import cu.uci.cegel.onei.sigipipc.persistence.Region;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {

    //QUERYS!!!!!!

    List<Region> regiones();

    List<Region> regionesPaging(int page, int size);

    Region regionById(Long id);

    Long totalRegiones();

    List<Region> regionByDescripcion(String description, int page, int size);

    Integer totalRegionByDescripciones(String descripcion);


    //MUTATIONS!!!!!!

    Region registrarRegion(RegionInput regionInput);

    Region actualizarRegion(RegionInput regionInput);

}
