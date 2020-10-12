package cu.uci.cegel.onei.sigipbase.web.traza.dto;

import cu.uci.cegel.onei.sigipbase.domain.trazas.Traza;
import cu.uci.cegel.onei.sigipbase.web.traza.TrazaController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class TrazaResourceAssembler extends ResourceAssemblerSupport<Traza, TrazaResource> {

    public TrazaResourceAssembler() {
        super(TrazaController.class, TrazaResource.class);
    }

    @Override
    public TrazaResource toResource(Traza traza) {
        TrazaResource resource = new TrazaResource();
        Link selfLink = ControllerLinkBuilder.linkTo(methodOn(TrazaController.class).obtenerTraza(traza.getId())).withSelfRel();
        resource.add(selfLink);
        resource.setIdTraza(traza.getId());
        resource.setDescripcion(traza.getDescripcion());
        resource.setFecha(traza.getFecha() != null ? traza.getFecha().toString() : null);
        resource.setUsuario(traza.getUsuario());
        resource.setIp(traza.getIp());
        resource.setTipoDeTraza(traza.getTipoDeTraza());
        resource.setTipoOperacion(traza.getTipoOperacion());
        return resource;
    }

    public List<TrazaResource> toResources(List<Traza> usuarios) {
        return usuarios.stream().map(this::toResource).collect(Collectors.toList());
    }
}
