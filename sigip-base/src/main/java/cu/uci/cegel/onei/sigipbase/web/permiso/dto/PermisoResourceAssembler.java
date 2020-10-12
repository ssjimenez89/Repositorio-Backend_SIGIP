package cu.uci.cegel.onei.sigipbase.web.permiso.dto;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.web.permiso.PermisoController;
import cu.uci.cegel.onei.sigipbase.web.usuario.UsuarioController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PermisoResourceAssembler extends ResourceAssemblerSupport<Permiso, PermisoResource> {

    public PermisoResourceAssembler() {
        super(PermisoController.class, PermisoResource.class);
    }

    @Override
    public PermisoResource toResource(Permiso permiso) {
        PermisoResource resource = new PermisoResource();
        Link selfLink = ControllerLinkBuilder.linkTo(methodOn(UsuarioController.class).obtenerUsuario(permiso.getId())).withSelfRel();
        resource.add(selfLink);
        resource.setIdpermiso(permiso.getId());
        resource.setPermiso(permiso.getPermiso());
        resource.setDescription(permiso.getDescription());
        resource.setActivo(permiso.getActivo());
        return resource;
    }

    public List<PermisoResource> toResources(List<Permiso> permisos) {
        return permisos.stream().map(this::toResource).collect(Collectors.toList());
    }
}
