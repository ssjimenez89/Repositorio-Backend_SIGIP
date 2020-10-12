package cu.uci.cegel.onei.sigipipc.resolvers.region;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.model.RegionInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import cu.uci.cegel.onei.sigipipc.persistence.Region;
import cu.uci.cegel.onei.sigipipc.services.CargoService;
import cu.uci.cegel.onei.sigipipc.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "RegionResolver")
public class RegionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private RegionService regionService;


    //QUERYS!!!!!!

    public List<Region> regiones() {
        return regionService.regiones();
    }

    public List<Region> regionesPaging(int page, int size){
        return regionService.regionesPaging(page, size);
    }

    public Region regionById(Long id) {
        return regionService.regionById(id);
    }

    public Long totalRegiones() {
        return regionService.totalRegiones();
    }

    public List<Region> regionByDescripcion(String descripcion, int page, int size){
        return regionService.regionByDescripcion(descripcion,page,size);
    }

    public Integer totalRegionByDescripciones(String descripcion){
        return regionService.totalRegionByDescripciones(descripcion);
    }



    //MUTATIONS!!!!!!

    public Region registrarRegion(RegionInput regionInput) {
        return regionService.registrarRegion(regionInput);
    }

    public Region actualizarRegion(RegionInput regionInput) {
        return regionService.actualizarRegion(regionInput);
    }

}
