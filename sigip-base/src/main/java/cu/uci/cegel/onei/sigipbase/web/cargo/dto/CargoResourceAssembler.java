package cu.uci.cegel.onei.sigipbase.web.cargo.dto;

import cu.uci.cegel.onei.sigipbase.domain.cargo.Cargo;
import cu.uci.cegel.onei.sigipbase.web.cargo.CargoController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CargoResourceAssembler extends ResourceAssemblerSupport<Cargo, CargoResource> {

    public CargoResourceAssembler() {
        super(CargoController.class, CargoResource.class);
    }

    @Override
    public CargoResource toResource(Cargo cargo) {
        CargoResource resource = new CargoResource();
        resource.setIdentificador(cargo.getId());
        resource.setDescripcion(cargo.getDescripcion());
        resource.setActivo(cargo.getActivo());
        return resource;
    }

    public List<CargoResource> toResources(List<Cargo> cargo) {
        return cargo.stream().map(this::toResource).collect(Collectors.toList());
    }

}
