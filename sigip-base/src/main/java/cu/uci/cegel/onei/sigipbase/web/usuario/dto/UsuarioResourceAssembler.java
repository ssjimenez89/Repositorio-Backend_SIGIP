package cu.uci.cegel.onei.sigipbase.web.usuario.dto;

import cu.uci.cegel.onei.sigipbase.domain.usuario.Usuario;
import cu.uci.cegel.onei.sigipbase.web.usuario.UsuarioController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UsuarioResourceAssembler extends ResourceAssemblerSupport<Usuario, UsuarioResource> {

    public UsuarioResourceAssembler() {
        super(UsuarioController.class, UsuarioResource.class);
    }

    @Override
    public UsuarioResource toResource(Usuario usuario) {
        UsuarioResource resource = new UsuarioResource();
        Link selfLink = ControllerLinkBuilder.linkTo(methodOn(UsuarioController.class).obtenerUsuario(usuario.getId())).withSelfRel();
        resource.add(selfLink);
        resource.setIdTrabajador(usuario.getId());
        resource.setPrimernombre(usuario.getPrimernombre());
        resource.setSegundonombre(usuario.getSegundonombre());
        resource.setPrimerapellido(usuario.getPrimerapellido());
        resource.setSegundoapellido(usuario.getSegundoapellido());
        resource.setActivo(usuario.isEnabled());
//        resource.setFechainicio(usuario.getFechainicio().toString());
//        resource.setFechafin(usuario.getFechafin() != null ? usuario.getFechafin().toString() : "");
//        resource.setCarnetidentidad(usuario.getCarnetidentidad());
        resource.setEmail(usuario.getCorreo());
        resource.setNombrecompleto(usuario.getNombreCompleto());
        if (usuario.getCargo() != null) {
            resource.setCargo(usuario.getCargo().getId());
            resource.setDenom_cargo(usuario.getCargo().getDescripcion());
        }
        if (usuario.getDpa() != null) {
            resource.setDpa(usuario.getDpa().getId());
            resource.setCod_dpa(usuario.getDpa().getCodigodpa());
        }
        if (usuario.getUsername() != null) {
            resource.setUsername(usuario.getUsername());
//            resource.setPassword(usuario.getPassword());
            resource.setPermisos(usuario.getPermisos());
            resource.setRoles(usuario.getRoles());
            //auxiliares(usuario,resource);
        }
        return resource;
    }

    public List<UsuarioResource> toResources(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toResource).collect(Collectors.toList());
    }
//ya no hace falta
//    private void auxiliares(Usuario usuario,UsuarioResource resource){
//        Set<Permiso> permisos_del_rol_temp = new HashSet<>();
//        Set<Permiso> permisos_del_usuario = new HashSet<>();
//        Set<Permiso> permisos_del_rol = new HashSet<>();
//        Set<Long> permisos_del_rol_id = new HashSet<>();
//
//        usuario.getRoles().stream().forEach((e)-> permisos_del_rol_temp.addAll(e.getPermisos()));
//        usuario.getPermisos().stream().forEach((e)-> permisos_del_usuario.add(e));
//        permisos_del_rol_temp.stream().forEach((e)->permisos_del_rol.add(e));
//
//        permisos_del_usuario.removeAll(permisos_del_rol);
//        resource.setPermisos_aislados(permisos_del_usuario);
//        permisos_del_rol.stream().forEach((e)->permisos_del_rol_id.add(e.getId()));
//        resource.setPermisos_roles_id(permisos_del_rol_id);
//    }
}
