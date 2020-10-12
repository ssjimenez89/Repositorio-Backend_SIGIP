package cu.uci.cegel.onei.sigipbase.web.usuario;

import cu.uci.cegel.onei.sigipbase.domain.usuario.Usuario;
import cu.uci.cegel.onei.sigipbase.domain.usuario.UsuarioService;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TipoOperacion;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TiposTraza;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.notifier.Notifier;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilValidacion;
import cu.uci.cegel.onei.sigipbase.kernel.CONSTANTS;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioDTO;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioForm;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioResource;
import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioResourceAssembler;
import cu.uci.cegel.security.utils.HeaderUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = CONSTANTS.ENTITY_API, produces = "application/json")
public class UsuarioController {

    private final @NonNull
    UsuarioService usuarioService;
    private final @NonNull
    UsuarioResourceAssembler usuarioResourceAssembler;
    private final @NonNull
    PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    private Notifier notifier;

    private static final String ENTITY_URI = "/usuario";
    private static final String ENTITY_NAME = "usuario";

    @PostMapping(path = ENTITY_URI, consumes = "application/json")
    public ResponseEntity<UsuarioResource> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO.getPassword().equals(usuarioDTO.getConfirmpassword())) {
                if (!usuarioService.obtenerUsuarioPorUsername(usuarioDTO.getUsername()).isPresent()) {
                    Usuario usuario = usuarioService.registrarUsuario(usuarioDTO);
                    notifier.publish(TiposTraza.FUNCIONAL, "Se ha creado el " + ENTITY_NAME + " " + usuario.getUsername(), TipoOperacion.GENERIC_REGISTRAR);
                    return ResponseEntity.created(new URI(CONSTANTS.ENTITY_API + ENTITY_URI + "/" + usuario.getId()))
                            .headers(HeaderUtil.infoAlert(" Se ha creado el " + ENTITY_NAME + " " + usuario.getUsername() + "."))
                            .body(usuarioResourceAssembler.toResource(usuario));
                } else {
                    return ResponseEntity.badRequest()
                            .headers(HeaderUtil.badRequestAlert("Ya existe un " + ENTITY_NAME + " con el usuario " + usuarioDTO.getUsername() + ".")).build();
                }
            } else {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("Las contraseñas no coinciden.")).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede registrar.")).build();
        }
    }

    @PutMapping(path = ENTITY_URI + "/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResource> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            if (usuarioService.obtenerUsuario(id).isPresent()) {
                Usuario usuario = usuarioService.actualizarUsuario(id, usuarioDTO);
                notifier.publish(TiposTraza.FUNCIONAL, "Se ha modificado el usuario " + usuario.getUsername(), TipoOperacion.GENERIC_MODIFICAR);
                return ResponseEntity.ok()
                        .headers(HeaderUtil.infoAlert("Se ha modificado el usuario " + usuario.getUsername() + "."))
                        .body(usuarioResourceAssembler.toResource(usuario));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se encuentra en el servidor.")).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede actualizar.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/{id}")
    public ResponseEntity<UsuarioResource> obtenerUsuario(@PathVariable Long id) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            Optional<Usuario> objUsuario = usuarioService.obtenerUsuario(id);
            //notifier.publish(TiposTraza.FUNCIONAL, "Obteniendo los datos del usuario " + objUsuario.get().getUsername(), TipoOperacion.GENERIC_OBTENER);
            return objUsuario.map(usuario -> ResponseEntity.ok()
                    .body(usuarioResourceAssembler.toResource(usuario)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }

    @SuppressWarnings("unchecked")
    @PostMapping(path = ENTITY_URI + "/list", consumes = "application/json")
    public ResponseEntity<PagedResources<UsuarioResource>> obtenerUsuarios(Pageable pageable, @RequestBody UsuarioForm usuarioForm) {
        try {
            Page<Usuario> usuarioPage = usuarioService.obtenerUsuarios(usuarioForm, pageable);
            if (usuarioForm.getPrimernombre() != null || usuarioForm.getPrimerapellido() != null || usuarioForm.getSegundoapellido() != null
                    || usuarioForm.getDpa() != null || usuarioForm.getCargo() != null) {
                if (usuarioPage.getTotalElements() != 0) {
                    return ResponseEntity.ok()
                            .body(pagedResourcesAssembler.toResource(usuarioPage, usuarioResourceAssembler));
                } else
                    return ResponseEntity.ok()
                            .headers(HeaderUtil.infoAlert("La búsqueda no arrojó resultados."))
                            .body(pagedResourcesAssembler.toResource(usuarioPage, usuarioResourceAssembler));
            } else
                return ResponseEntity.ok()
                        .body(pagedResourcesAssembler.toResource(usuarioPage, usuarioResourceAssembler));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }


    @GetMapping(path = ENTITY_URI + "/username/{username}")
    public ResponseEntity<UsuarioResource> obtenerUsuarioPorUsername(@PathVariable String username) {
        try {
            return usuarioService.obtenerUsuarioPorUsername(username)
                    .map(usuario -> ResponseEntity.ok()
                            .body(usuarioResourceAssembler.toResource(usuario))).orElseGet(() -> ResponseEntity.badRequest()
                            .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " con nombre de usuario " + username + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/all")
    public ResponseEntity<List<UsuarioResource>> obtenerTodosUsuarios() {
        try {
            return ResponseEntity.ok().body(usuarioResourceAssembler.toResources(usuarioService.obtenerUsuarios()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/rol/{rol}", consumes = "application/json")
    public ResponseEntity<List<UsuarioResource>> obtenerUsuarioPorRol(@PathVariable String rol) {
        try {
            return ResponseEntity.ok()
                    .body(usuarioResourceAssembler.toResources(usuarioService.obtenerUsuariosPorPermiso(rol)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo el " + ENTITY_NAME + " en el servidor.")).build();
        }
    }

    @PostMapping(path = ENTITY_URI + "/cambiaractivo/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResource> activarDesactivarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.activarDesactivarUsuario(id, usuarioDTO);
            String activo = usuario.isEnabled() ? "activado" : "desactivado";
            notifier.publish(TiposTraza.FUNCIONAL, "Se ha " + activo + " el " + ENTITY_NAME + " " + usuario.getUsername(), TipoOperacion.GENERIC_DESACTIVAR);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.infoAlert("Se ha " + activo + " el " + ENTITY_NAME + " " + usuario.getUsername() + "."))
                    .body(usuarioResourceAssembler.toResource(usuario));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error al desactivar el " + ENTITY_NAME + " en el servidor.")).build();
        }
    }

    @PostMapping(path = ENTITY_URI + "/cambiarpasswd/{id}", consumes = "application/json")
    public ResponseEntity<UsuarioResource> cambiarPassword(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.cambiarPassword(id, usuarioDTO);
            notifier.publish(TiposTraza.FUNCIONAL, "Cambio de contraseña del " + ENTITY_NAME + " " + usuario.getUsername(), TipoOperacion.GENERIC_MODIFICAR);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.infoAlert("Se ha cambiado la contraseña correctamente."))
                    .body(usuarioResourceAssembler.toResource(usuario));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error cambiando la contraseña del " + ENTITY_NAME + " en el servidor.")).build();
        }
    }

}
