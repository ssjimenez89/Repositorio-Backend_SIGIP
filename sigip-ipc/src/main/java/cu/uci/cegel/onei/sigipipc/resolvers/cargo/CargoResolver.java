package cu.uci.cegel.onei.sigipipc.resolvers.cargo;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import cu.uci.cegel.onei.sigipipc.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "CargoResolver")
public class CargoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private CargoService cargoService;


    //QUERYS!!!!!!

    public Cargo cargoById(Long id) {
        return cargoService.obtenerCargo(id);
    }

    public List<Cargo> cargos() {
        return cargoService.listarCargos();
    }

    public Long totalCargos() {
        return cargoService.totalCargos();
    }

    public List<Cargo> cargoByDescription(String descripcion, int page, int size){
        return cargoService.cargosPorDescripcion(descripcion,page,size);
    }

    public Integer totalCargoByDescription(String descripcion){
        return cargoService.totalCargosPorDescripcion(descripcion);
    }

    public List<Cargo> cargosPaging(int page, int size){
        return cargoService.listarCargosPaging(page, size);
    }

    //MUTATIONS!!!!!!

    public Cargo registrarCargo(CargoInput cargoInput) {
        return cargoService.registrarCargo(cargoInput);
    }

    public Cargo actualizarCargo(CargoInput cargoInput) {
        return cargoService.actualizarCargo(cargoInput);
    }
}
