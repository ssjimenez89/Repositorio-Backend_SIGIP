package cu.uci.cegel.onei.sigipbase.web.dpa.dto;

import cu.uci.cegel.onei.sigipbase.domain.dpa.Dpa;
import cu.uci.cegel.onei.sigipbase.web.dpa.DpaController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DpaResourceAssembler extends ResourceAssemblerSupport<Dpa, DpaResource> {

    public DpaResourceAssembler() {
        super(DpaController.class, DpaResource.class);
    }

    @Override
    public DpaResource toResource(Dpa dpa) {
        DpaResource resource = new DpaResource();
        resource.setIdentificador(dpa.getId());
        resource.setCodigodpa(dpa.getCodigodpa());
        resource.setDescripcion(dpa.getDescripcion());
        resource.setIdpadre(dpa.getIdpadre());
        resource.setActivo(dpa.getActivo());
        resource.setCodigo_descripcion(dpa.getCodigodpa().concat("-").concat(dpa.getDescripcion()));
        return resource;
    }

    public List<DpaResource> toResources(List<Dpa> dpa) {
        return dpa.stream().map(this::toResource).collect(Collectors.toList());
    }

}
