package cu.uci.cegel.onei.sigipbase.web.rol.dto;

import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import cu.uci.cegel.onei.sigipbase.web.rol.RolController;
import cu.uci.cegel.onei.sigipbase.web.usuario.UsuarioController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RolResourceAssembler extends ResourceAssemblerSupport<Rol, RolResource> {

    public RolResourceAssembler() {
        super(RolController.class, RolResource.class);
    }

    @Override
    public RolResource toResource(Rol rol) {
        RolResource resource = new RolResource();
        Link selfLink = ControllerLinkBuilder.linkTo(methodOn(UsuarioController.class).obtenerUsuario(rol.getId())).withSelfRel();
        resource.add(selfLink);
        resource.setIdRol(rol.getId());
        resource.setRol(rol.getRol());
        resource.setDescription(rol.getDescription());
        resource.setActivo(rol.getActivo());
        resource.setPermisos(rol.getPermisos());
        return resource;
    }

    public List<RolResource> toResources(List<Rol> rol) {
        return rol.stream().map(this::toResource).collect(Collectors.toList());
    }
}
